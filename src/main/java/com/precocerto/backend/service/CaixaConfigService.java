package com.precocerto.backend.service;

import com.precocerto.backend.dto.request.CaixaConfigDTORequest;
import com.precocerto.backend.dto.response.CaixaConfigDTOResponse;
import com.precocerto.backend.infrastructure.entity.CaixaConfigEntity;
import com.precocerto.backend.infrastructure.repository.CaixaConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaixaConfigService {
    private final CaixaConfigRepository repository;

    public CaixaConfigDTOResponse buscarConfiguracao() {
        return paraDTO(buscarOuCriar());
    }

    public CaixaConfigDTOResponse atualizarConfiguracao(CaixaConfigDTORequest dto) {
        CaixaConfigEntity entity = buscarOuCriar();
        entity.setCapitalInicial(dto.capitalInicial() == null ? 0.0 : dto.capitalInicial());
        return paraDTO(repository.save(entity));
    }

    public double buscarCapitalInicial() {
        return buscarOuCriar().getCapitalInicial();
    }

    private CaixaConfigEntity buscarOuCriar() {
        return repository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> repository.save(CaixaConfigEntity.builder()
                        .capitalInicial(0.0)
                        .build()));
    }

    private CaixaConfigDTOResponse paraDTO(CaixaConfigEntity entity) {
        return CaixaConfigDTOResponse.builder()
                .id(entity.getId())
                .capitalInicial(entity.getCapitalInicial())
                .dataAtualizacao(entity.getDataAtualizacao())
                .build();
    }
}
