package com.precocerto.backend.converter;

import com.precocerto.backend.dto.request.ReceitaDTORequest;
import com.precocerto.backend.dto.response.ReceitaDTOResponse;
import com.precocerto.backend.infrastructure.entity.ReceitaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReceitaConverter {
    private final ItemReceitaConverter itemReceitaConverter;

    public ReceitaDTOResponse paraDTO(ReceitaEntity entity) {
        return ReceitaDTOResponse.builder()
                .id(entity.getId())
                .nomeReceita(entity.getNomeReceita())
                .tempoGas(entity.getTempoGas())
                .tempoEnergia(entity.getTempoEnergia())
                .custoTotal(entity.getCustoTotal())
                .margemLucro(entity.getMargemLucro() != null ? entity.getMargemLucro() : 100.0)
                .precoSugerido(entity.getPrecoSugerido())
                .itensReceita(entity.getItensReceita()
                        .stream()
                        .map(itemReceitaConverter::paraDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ReceitaEntity paraEntity(ReceitaDTORequest dto) {
        return ReceitaEntity.builder()
                .nomeReceita(dto.nomeReceita())
                .tempoGas(dto.tempoGas())
                .tempoEnergia(dto.tempoEnergia())
                .margemLucro(dto.margemLucro())
                .itensReceita(dto.itensReceita()
                        .stream()
                        .map(itemReceitaConverter::paraEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
