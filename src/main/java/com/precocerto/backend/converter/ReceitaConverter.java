package com.precocerto.backend.converter;

import com.precocerto.backend.dto.request.ReceitaDTORequest;
import com.precocerto.backend.dto.request.ItemReceitaDTORequest;
import com.precocerto.backend.dto.response.ReceitaDTOResponse;
import com.precocerto.backend.infrastructure.entity.ItemReceitaEntity;
import com.precocerto.backend.infrastructure.entity.ReceitaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Locale;
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
                .rendimentoReceita(entity.getRendimentoReceita())
                .quantidadeRendimento(entity.getQuantidadeRendimento())
                .itensReceita((entity.getItensReceita() == null ? Collections.<ItemReceitaEntity>emptyList() : entity.getItensReceita())
                        .stream()
                        .map(itemReceitaConverter::paraDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ReceitaEntity paraEntity(ReceitaDTORequest dto) {
        return ReceitaEntity.builder()
                .nomeReceita(normalizarTexto(dto.nomeReceita()))
                .tempoGas(dto.tempoGas())
                .tempoEnergia(dto.tempoEnergia())
                .margemLucro(dto.margemLucro())
                .rendimentoReceita(dto.rendimentoReceita())
                .quantidadeRendimento(dto.quantidadeRendimento())
                .itensReceita((dto.itensReceita() == null ? Collections.<ItemReceitaDTORequest>emptyList() : dto.itensReceita())
                        .stream()
                        .map(itemReceitaConverter::paraEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    private String normalizarTexto(String valor) {
        return valor == null ? null : valor.trim().toUpperCase(Locale.ROOT);
    }
}
