package com.precocerto.backend.converter;

import com.precocerto.backend.dto.request.CompraDTORequest;
import com.precocerto.backend.dto.response.CompraDTOResponse;
import com.precocerto.backend.infrastructure.entity.CompraEntity;
import com.precocerto.backend.infrastructure.entity.InsumosEntity;
import org.springframework.stereotype.Component;

@Component
public class CompraConverter {
    public CompraDTOResponse paraDTO(CompraEntity entity) {
        return CompraDTOResponse.builder()
                .id(entity.getId())
                .insumoId(entity.getInsumo() == null ? null : entity.getInsumo().getId())
                .movimentacaoId(entity.getMovimentacao() == null ? null : entity.getMovimentacao().getId())
                .nomeInsumo(entity.getInsumo() == null ? null : entity.getInsumo().getNomeInsumo())
                .unidadeMedida(entity.getInsumo() == null ? null : entity.getInsumo().getUnidadeMedida().name())
                .quantidade(entity.getQuantidade())
                .valorTotal(entity.getValorTotal())
                .custoUnitarioCompra(entity.getCustoUnitarioCompra())
                .dataCompra(entity.getDataCompra())
                .build();
    }

    public CompraEntity paraEntity(CompraDTORequest dto, InsumosEntity insumo) {
        double quantidade = dto.quantidade() == null ? 0.0 : dto.quantidade();
        double valorTotal = dto.valorTotal() == null ? 0.0 : dto.valorTotal();

        return CompraEntity.builder()
                .insumo(insumo)
                .quantidade(quantidade)
                .valorTotal(valorTotal)
                .custoUnitarioCompra(quantidade <= 0 ? 0.0 : valorTotal / quantidade)
                .build();
    }
}
