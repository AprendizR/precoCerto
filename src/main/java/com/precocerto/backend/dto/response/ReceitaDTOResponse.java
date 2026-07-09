package com.precocerto.backend.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ReceitaDTOResponse(
        Long id,
        String nomeReceita,
        Double tempoGas,
        Double tempoEnergia,
        Double custoTotal,
        List<ItemReceitaDTOResponse> itensReceita
) {
}
