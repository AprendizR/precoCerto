package com.precocerto.backend.dto.response;

import com.precocerto.backend.enums.Tipo;
import com.precocerto.backend.infrastructure.entity.InsumosEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MovimentacaoDTOResponse(
        Long id,
        InsumosEntity insumo,
        Long compraId,
        Tipo tipo,
        Double quantidade,
        Double precoCompra,
        LocalDateTime dataMovimentacao,
        Boolean estorno
) {
}
