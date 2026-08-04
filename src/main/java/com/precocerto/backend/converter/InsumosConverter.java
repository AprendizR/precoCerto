package com.precocerto.backend.converter;

import com.precocerto.backend.dto.request.InsumosDTORequest;
import com.precocerto.backend.dto.response.InsumosDTOResponse;
import com.precocerto.backend.infrastructure.entity.InsumosEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class InsumosConverter {
    public InsumosDTOResponse paraDTO(InsumosEntity entity) {
        return InsumosDTOResponse.builder()
                .id(entity.getId())
                .nomeInsumo(entity.getNomeInsumo())
                .quantidadeAtual(entity.getQuantidadeAtual())
                .unidadeMedida(entity.getUnidadeMedida())
                .custoMedioUnitario(entity.getCustoMedioUnitario())
                .dataCriacao(entity.getDataCriacao())
                .build();
    }

    public InsumosEntity paraEntity(InsumosDTORequest dtoRequest) {
        return InsumosEntity.builder()
                .nomeInsumo(normalizarTexto(dtoRequest.nomeInsumo()))
                .unidadeMedida(dtoRequest.unidadeMedida())
                .build();
    }

    private String normalizarTexto(String valor) {
        return valor == null ? null : valor.trim().toUpperCase(Locale.ROOT);
    }
}
