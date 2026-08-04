package com.precocerto.backend.dto.response;

import com.precocerto.backend.enums.FormaPagamento;
import com.precocerto.backend.enums.StatusVenda;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record VendaDTOResponse(
        Long id,
        String nomeReceita,
        FormaPagamento formaPagamento,
        Double precoVenda,
        LocalDateTime dataVenda,
        StatusVenda status
) {
}
