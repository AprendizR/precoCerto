package com.precocerto.backend.dto.request;

import com.precocerto.backend.enums.Tipo;
import lombok.Builder;

@Builder
public record MovimentacaoDTORequest(
        InsumoRefDTO insumo,
        Tipo tipo,
        Double quantidade,
        Double precoCompra
) {
}