package com.precocerto.backend.dto.response;

import lombok.Builder;

@Builder
public record DreResumoDTOResponse(
        Double receitaBruta,
        Double cmvInsumos,
        Double despesasFixas,
        Double lucroOperacional,
        Double capitalInicial,
        Double totalCompras,
        Double saldoDisponivel,
        Long vendasConcluidas
) {
}
