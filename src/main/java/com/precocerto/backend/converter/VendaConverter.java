package com.precocerto.backend.converter;

import com.precocerto.backend.dto.request.VendaDTORequest;
import com.precocerto.backend.dto.response.VendaDTOResponse;
import com.precocerto.backend.infrastructure.entity.ReceitaEntity;
import com.precocerto.backend.infrastructure.entity.VendaEntity;
import org.springframework.stereotype.Component;

@Component
public class VendaConverter {
    public VendaDTOResponse paraDTO(VendaEntity entity){
        return VendaDTOResponse.builder()
                .id(entity.getId())
                .nomeReceita(entity.getReceita().getNomeReceita())
                .formaPagamento(entity.getFormaPagamento())
                .precoVenda(entity.getPrecoVenda())
                .dataVenda(entity.getDataVenda())
                .build();
    }

    public VendaEntity paraEntity(VendaDTORequest dto){
        return VendaEntity.builder()
                .formaPagamento(dto.formaPagamento())
                .precoVenda(dto.precoVenda())
                .build();
    }

    public VendaEntity paraEntityReceita(VendaDTORequest dto, ReceitaEntity receita){
        return VendaEntity.builder()
                .receita(receita)
                .formaPagamento(dto.formaPagamento())
                .precoVenda(dto.precoVenda())
                .build();
    }
}
