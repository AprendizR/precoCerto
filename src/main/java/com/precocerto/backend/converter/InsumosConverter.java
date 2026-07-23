package com.precocerto.backend.converter;

import com.precocerto.backend.dto.request.InsumosDTORequest;
import com.precocerto.backend.dto.response.InsumosDTOResponse;
import com.precocerto.backend.infrastructure.entity.InsumosEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InsumosConverter {
    public InsumosDTOResponse paraDTO(InsumosEntity entity){
        return InsumosDTOResponse.builder()
                .id(entity.getId())
                .nomeInsumo(entity.getNomeInsumo())
                .quantidadeAtual(entity.getQuantidadeAtual())
                .unidadeMedida(entity.getUnidadeMedida())
                .custoMedioUnitario(entity.getCustoMedioUnitario())
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    public InsumosEntity paraEntity(InsumosDTORequest dtoRequest){
        return InsumosEntity.builder()
                .nomeInsumo(dtoRequest.nomeInsumo())
                .unidadeMedida(dtoRequest.unidadeMedida())
                .dataCriacao(LocalDateTime.now())
                .build();
    }
}
