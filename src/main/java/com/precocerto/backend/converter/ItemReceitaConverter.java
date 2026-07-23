package com.precocerto.backend.converter;

import com.precocerto.backend.dto.request.ItemReceitaDTORequest;
import com.precocerto.backend.dto.response.ItemReceitaDTOResponse;
import com.precocerto.backend.infrastructure.entity.ItemReceitaEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemReceitaConverter {
    public ItemReceitaDTOResponse paraDTO(ItemReceitaEntity entity){
        return ItemReceitaDTOResponse.builder()
                .id(entity.getId())
                .insumoId(entity.getInsumos().getId())
                .nomeInsumo(entity.getInsumos().getNomeInsumo())
                .receitaId(entity.getReceita().getId())
                .quantidadeUsada(entity.getQuantidadeUsada())
                .build();
    }

    public ItemReceitaEntity paraEntity(ItemReceitaDTORequest dto){
        return ItemReceitaEntity.builder()
                .quantidadeUsada(dto.quantidadeUsada())
                .build();
    }
}
