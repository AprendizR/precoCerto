package com.precocerto.backend.dto.request;

import lombok.Builder;

@Builder
public record CustosFixosDTORequest(
        Double custoPorMinutoGas,
        Double custoPorMinutoEnergia
) {
}
