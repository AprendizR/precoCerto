package com.precocerto.backend.dto.request;

import lombok.Builder;
import java.util.List;

@Builder
public record ReceitaDTORequest (
        String nomeReceita,
        Double tempoGas,
        Double tempoEnergia,
        Double margemLucro,
        List<ItemReceitaDTORequest> itensReceita
) {
}
