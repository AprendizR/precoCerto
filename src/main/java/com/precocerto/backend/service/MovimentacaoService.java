package com.precocerto.backend.service;

import com.precocerto.backend.converter.MovimentacaoConverter;
import com.precocerto.backend.dto.request.InsumoRefDTO;
import com.precocerto.backend.dto.request.MovimentacaoDTORequest;
import com.precocerto.backend.dto.response.MovimentacaoDTOResponse;
import com.precocerto.backend.enums.Tipo;
import com.precocerto.backend.infrastructure.entity.InsumosEntity;
import com.precocerto.backend.infrastructure.entity.ItemReceitaEntity;
import com.precocerto.backend.infrastructure.entity.MovimentacaoEntity;
import com.precocerto.backend.infrastructure.entity.VendaEntity;
import com.precocerto.backend.infrastructure.exception.ConflictException;
import com.precocerto.backend.infrastructure.repository.InsumosRepository;
import com.precocerto.backend.infrastructure.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {
    private final MovimentacaoRepository repository;
    private final InsumosRepository insumosRepository;
    private final MovimentacaoConverter converter;

    @Transactional
    public MovimentacaoDTOResponse criarMovimentacao(MovimentacaoDTORequest dto) {
        InsumosEntity insumo = insumosRepository.findById(dto.insumo().id()).orElseThrow(() -> new RuntimeException("Insumo não encontrado"));
        double preco = (dto.tipo() == Tipo.SAIDA) ? 0.0 : (dto.precoCompra() == null ? 0.0 : dto.precoCompra());

        if (dto.tipo() == Tipo.ENTRADA) {
            if (dto.quantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
            }

            double totalAtual = insumo.getQuantidadeAtual() * insumo.getCustoMedioUnitario();
            double novaQuantidade = insumo.getQuantidadeAtual() + dto.quantidade();
            double precoCompra = (dto.precoCompra() != null) ? dto.precoCompra() : 0.0;
            double novoCusto = (totalAtual + precoCompra) / novaQuantidade;
            double custoArredondado = Math.round(novoCusto * 1_000_000.0) / 1_000_000.0;

            insumo.setCustoMedioUnitario(custoArredondado);
            insumo.setQuantidadeAtual(novaQuantidade);
        } else {
            if (insumo.getQuantidadeAtual() < dto.quantidade()) {
                throw new ConflictException("Quantia insuficiente");
            }
            insumo.setQuantidadeAtual(insumo.getQuantidadeAtual() - dto.quantidade());
        }

        insumosRepository.save(insumo);

        MovimentacaoEntity entity = converter.paraEntity(dto);
        entity.setPrecoCompra(preco);
        entity.setInsumos(insumo);
        return converter.paraDTO(repository.save(entity));
    }

    public List<MovimentacaoDTOResponse> listarMovimentacoes() {
        return repository.findAllWithInsumos()
                .stream()
                .map(converter::paraDTO)
                .collect(Collectors.toList());
    }

    public MovimentacaoDTOResponse buscarMovimentacao(Long id) {
        return repository.findByIdWithInsumos(id).map(converter::paraDTO).orElseThrow(() -> new RuntimeException("Movimentação não encotrada"));
    }

    @Transactional
    public void estornarMovimentacao(Long id) {
        MovimentacaoEntity original = repository.findByIdWithInsumos(id).orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));

        double precoEstorno = (original.getTipo() == Tipo.SAIDA) ? original.getInsumos().getCustoMedioUnitario() : original.getPrecoCompra();

        MovimentacaoDTORequest estorno = new MovimentacaoDTORequest(
                new InsumoRefDTO(original.getInsumos().getId()),
                original.getTipo() == Tipo.ENTRADA ? Tipo.SAIDA : Tipo.ENTRADA,
                original.getQuantidade(),
                precoEstorno
        );

        criarMovimentacao(estorno);
    }

    @Transactional
    public void registrarBaixaReceita(List<ItemReceitaEntity> itens, VendaEntity venda) {
        itens.forEach(item -> {
            MovimentacaoEntity mov = MovimentacaoEntity.builder()
                    .insumos(item.getInsumos())
                    .venda(venda)
                    .tipo(Tipo.SAIDA)
                    .quantidade(item.getQuantidadeUsada())
                    .precoCompra(0.0)
                    .build();

            repository.save(mov);

            InsumosEntity insumo = item.getInsumos();
            insumo.setQuantidadeAtual(insumo.getQuantidadeAtual() - item.getQuantidadeUsada());
            insumosRepository.save(insumo);
        });
    }

}
