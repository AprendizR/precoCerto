package com.precocerto.backend.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CompraDTOResponse(
        Long id,
        Long insumoId,
        Long movimentacaoId,
        String nomeInsumo,
        String unidadeMedida,
        Double quantidade,
        Double valorTotal,
        Double custoUnitarioCompra,
        LocalDateTime dataCompra
) {
}
