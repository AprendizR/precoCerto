package com.precocerto.backend.dto.request;

import com.precocerto.backend.enums.UnidadeMedida;
import lombok.Builder;

@Builder
public record InsumosDTORequest (
        String nomeInsumo,
        UnidadeMedida unidadeMedida
)
{}
