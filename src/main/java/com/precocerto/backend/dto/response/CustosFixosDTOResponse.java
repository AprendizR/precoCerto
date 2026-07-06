package com.precocerto.backend.dto.response;

import lombok.Builder;

@Builder
public record CustosFixosDTOResponse(
        Long id,
        Double custoPorMinutoGas,
        Double custoPorMinutoEnergia
) {
}
