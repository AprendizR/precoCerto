package com.precocerto.backend.converter;

import com.precocerto.backend.dto.request.InsumosDTORequest;
import com.precocerto.backend.dto.response.InsumosDTOResponse;
import com.precocerto.backend.infrastructure.entity.InsumosEntity;
import org.springframework.stereotype.Component;

@Component
public class InsumosConverter {
    public InsumosDTOResponse paraDTO(InsumosEntity entity){
        return InsumosDTOResponse.builder()
                .id(entity.getId())
                .nomeInsumo(entity.getNomeInsumo())
                .quantidadeAtual(entity.getQuantidadeAtual())
                .unidadeMedida(entity.getUnidadeMedida())
                .custoMedioUnitario(entity.getCustoMedioUnitario())
                .build();
    }

    public InsumosEntity paraEntity(InsumosDTORequest dtoRequest){
        return InsumosEntity.builder()
                .nomeInsumo(dtoRequest.nomeInsumo())
                .unidadeMedida(dtoRequest.unidadeMedida())
                .quantidadeAtual(0.0)
                .build();
    }
}
