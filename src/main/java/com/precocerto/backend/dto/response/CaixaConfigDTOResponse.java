package com.precocerto.backend.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CaixaConfigDTOResponse(
        Long id,
        Double capitalInicial,
        LocalDateTime dataAtualizacao
) {
}
