package com.precocerto.backend.service;

import com.precocerto.backend.converter.VendaConverter;
import com.precocerto.backend.dto.request.VendaDTORequest;
import com.precocerto.backend.dto.response.VendaDTOResponse;
import com.precocerto.backend.enums.FormaPagamento;
import com.precocerto.backend.enums.RendimentoReceita;
import com.precocerto.backend.enums.StatusVenda;
import com.precocerto.backend.infrastructure.entity.MovimentacaoEntity;
import com.precocerto.backend.infrastructure.entity.ReceitaEntity;
import com.precocerto.backend.infrastructure.entity.VendaEntity;
import com.precocerto.backend.infrastructure.repository.MovimentacaoRepository;
import com.precocerto.backend.infrastructure.repository.ReceitaRepository;
import com.precocerto.backend.infrastructure.repository.VendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaRepository repository;
    private final ReceitaRepository receitaRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final MovimentacaoService movimentacaoService;
    private final VendaConverter converter;

    @Transactional
    public VendaDTOResponse adicionarVenda(VendaDTORequest dto) {
        ReceitaEntity receita = receitaRepository.findById(dto.receitaId()).orElseThrow(() ->
                new RuntimeException("Receita nao encontrada"));

        validarQuantidadeVendida(dto, receita);

        VendaEntity entity = converter.paraEntityReceita(dto, receita);
        if (entity.getPrecoVenda() == null) {
            entity.setPrecoVenda(calcularPrecoVendaSugerido(receita, dto.quantidadeVendida()));
        }

        if (entity.getFormaPagamento() == FormaPagamento.PIX || entity.getFormaPagamento() == FormaPagamento.DINHEIRO) {
            entity.setPrecoVenda(entity.getPrecoVenda());
        } else if (entity.getFormaPagamento() == FormaPagamento.DEBITO) {
            entity.setPrecoVenda(entity.getPrecoVenda() - (entity.getPrecoVenda() * 0.0075));
        } else {
            entity.setPrecoVenda(entity.getPrecoVenda() - (entity.getPrecoVenda() * 0.0269));
        }

        VendaEntity vendaSalva = repository.save(entity);

        double fatorRendimento = vendaSalva.getQuantidadeVendida() / receita.getQuantidadeRendimento();
        movimentacaoService.registrarBaixaReceita(receita.getItensReceita(), vendaSalva, fatorRendimento);

        return converter.paraDTO(vendaSalva);
    }

    public List<VendaDTOResponse> listarVendas() {
        return repository.findAll().stream().map(converter::paraDTO).collect(Collectors.toList());
    }

    public VendaDTOResponse buscarVenda(Long id) {
        return repository.findById(id).map(converter::paraDTO).orElseThrow(() -> new RuntimeException("Venda nao encontrada"));
    }

    public VendaDTOResponse atualizarVenda(Long id, VendaDTORequest dto) {
        VendaEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Venda nao encontrada"));
        if (dto.formaPagamento() != null) {
            entity.setFormaPagamento(dto.formaPagamento());
        }
        if (dto.precoVenda() != null) {
            entity.setPrecoVenda(dto.precoVenda());
        }
        return converter.paraDTO(repository.save(entity));
    }

    public void cancelarVenda(Long id) {
        VendaEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Venda nao encontrada"));

        if (entity.getStatus() == StatusVenda.CANCELADA) {
            throw new RuntimeException("Venda ja cancelada");
        }

        List<MovimentacaoEntity> movimentacoes = movimentacaoRepository.findByVendaId(id);
        movimentacoes.forEach(mov -> movimentacaoService.estornarMovimentacao(mov.getId()));
        entity.setStatus(StatusVenda.CANCELADA);
        repository.save(entity);
    }

    private void validarQuantidadeVendida(VendaDTORequest dto, ReceitaEntity receita) {
        if (dto.quantidadeVendida() == null || dto.quantidadeVendida() <= 0) {
            throw new IllegalArgumentException("A quantidade vendida deve ser maior que zero.");
        }
        if (receita.getQuantidadeRendimento() == null || receita.getQuantidadeRendimento() <= 0) {
            throw new IllegalArgumentException("A receita precisa ter uma quantidade de rendimento valida.");
        }
        if (dto.quantidadeVendida() > receita.getQuantidadeRendimento()) {
            throw new IllegalArgumentException("A quantidade vendida nao pode ser maior que o rendimento da receita.");
        }
    }

    private double calcularPrecoVendaSugerido(ReceitaEntity receita, Double quantidadeVendida) {
        double unidadeReferencia = receita.getRendimentoReceita() == RendimentoReceita.FATIA ? 1.0 : 500.0;
        double preco = receita.getPrecoSugerido() * (quantidadeVendida / unidadeReferencia);
        return Math.round(preco * 100.0) / 100.0;
    }
}
