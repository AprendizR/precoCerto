package com.precocerto.backend.service;

import com.precocerto.backend.converter.VendaConverter;
import com.precocerto.backend.dto.request.VendaDTORequest;
import com.precocerto.backend.dto.response.VendaDTOResponse;
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
                new RuntimeException("Receita não encontrada"));
        VendaEntity entity = converter.paraEntityReceita(dto, receita);
        VendaEntity vendaSalva = repository.save(entity);

        movimentacaoService.registrarBaixaReceita(receita.getItensReceita(), vendaSalva);

        return converter.paraDTO(vendaSalva);
    }

    public List<VendaDTOResponse> listarVendas() {
        return repository.findAll().stream().map(converter::paraDTO).collect(Collectors.toList());
    }

    public VendaDTOResponse buscarVenda(Long id) {
        return repository.findById(id).map(converter::paraDTO).orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }

    public VendaDTOResponse atualizarVenda(Long id, VendaDTORequest dto) {
        VendaEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada"));
        if (dto.formaPagamento() != null) {
            entity.setFormaPagamento(dto.formaPagamento());
        }
        if (dto.precoVenda() != null){
            entity.setPrecoVenda(dto.precoVenda());
        }
        return converter.paraDTO(repository.save(entity));
    }

    public void cancelarVenda(Long id){
        VendaEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        if (entity.getStatus() == StatusVenda.CANCELADA){
            throw new RuntimeException("Venda já cancelada");
        }

        List<MovimentacaoEntity> movimentacoes = movimentacaoRepository.findByVendaId(id);
        movimentacoes.forEach(mov -> movimentacaoService.estornarMovimentacao(mov.getId()));        ;
        entity.setStatus(StatusVenda.CANCELADA);
        repository.save(entity);
    }
}
