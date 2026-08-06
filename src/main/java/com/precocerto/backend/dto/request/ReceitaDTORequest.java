package com.precocerto.backend.dto.request;

import com.precocerto.backend.enums.RendimentoReceita;
import lombok.Builder;
import java.util.List;

@Builder
public record ReceitaDTORequest (
        String nomeReceita,
        Double tempoGas,
        Double tempoEnergia,
        Double margemLucro,
        RendimentoReceita rendimentoReceita,
        Double quantidadeRendimento,
        List<ItemReceitaDTORequest> itensReceita
) {
}
