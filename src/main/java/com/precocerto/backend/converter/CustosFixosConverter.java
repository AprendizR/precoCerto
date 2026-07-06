package com.precocerto.backend.converter;

import com.precocerto.backend.dto.request.CustosFixosDTORequest;
import com.precocerto.backend.dto.response.CustosFixosDTOResponse;
import com.precocerto.backend.infrastructure.entity.CustosFixosEntity;
import org.springframework.stereotype.Component;

@Component
public class CustosFixosConverter {
    public CustosFixosDTOResponse paraDTO(CustosFixosEntity entity){
        return CustosFixosDTOResponse.builder()
                .id(entity.getId())
                .custoPorMinutoGas(entity.getCustoPorMinutoGas())
                .custoPorMinutoEnergia(entity.getCustoPorMinutoEnergia())
                .build();
    }

    public CustosFixosEntity paraEntity(CustosFixosDTORequest dto){
        return CustosFixosEntity.builder()
                .custoPorMinutoGas(dto.custoPorMinutoGas())
                .custoPorMinutoEnergia(dto.custoPorMinutoEnergia())
                .build();
    }
}
