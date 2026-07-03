package com.precocerto.backend.dto.response;

import com.precocerto.backend.enums.UnidadeMedida;
import lombok.Builder;


import java.time.LocalDateTime;

@Builder
public record InsumosDTOResponse(
        Long id,
        String nomeInsumo,
        Double quantidadeAtual,
        UnidadeMedida unidadeMedida,
        Double custoMedioUnitario,
        LocalDateTime dataCriacao
) {
}
