package com.precocerto.backend.dto.response;

import com.precocerto.backend.enums.RendimentoReceita;
import lombok.Builder;

import java.util.List;

@Builder
public record ReceitaDTOResponse(
        Long id,
        String nomeReceita,
        Double tempoGas,
        Double tempoEnergia,
        Double custoTotal,
        Double margemLucro,
        Double precoSugerido,
        RendimentoReceita rendimentoReceita,
        Double quantidadeRendimento,
        List<ItemReceitaDTOResponse> itensReceita
) {
}
