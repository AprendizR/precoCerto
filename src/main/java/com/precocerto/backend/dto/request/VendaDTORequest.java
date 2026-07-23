package com.precocerto.backend.dto.request;

import com.precocerto.backend.enums.FormaPagamento;
import lombok.Builder;

@Builder
public record VendaDTORequest(
        Long receitaId,
        FormaPagamento formaPagamento,
        Double precoVenda
) {
}
