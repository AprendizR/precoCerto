package com.precocerto.backend.dto.response;

import lombok.Builder;

@Builder
public record ItemReceitaDTOResponse(
        Long id,
        Long insumoId,
        String nomeInsumo,
        Long receitaId,
        Double quantidadeUsada
) {
}
