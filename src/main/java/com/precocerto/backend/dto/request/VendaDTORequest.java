package com.precocerto.backend.dto.request;

import com.precocerto.backend.enums.FormaPagamento;
import com.precocerto.backend.infrastructure.entity.ReceitaEntity;
import lombok.Builder;

@Builder
public record VendaDTORequest(
        Long receitaId,
        FormaPagamento formaPagamento,
        Double precoVenda

) {
}
