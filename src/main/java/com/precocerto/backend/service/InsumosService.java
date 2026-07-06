package com.precocerto.backend.service;

import com.precocerto.backend.converter.InsumosConverter;
import com.precocerto.backend.dto.request.InsumosDTORequest;
import com.precocerto.backend.dto.response.InsumosDTOResponse;
import com.precocerto.backend.infrastructure.entity.InsumosEntity;
import com.precocerto.backend.infrastructure.exception.ConflictException;
import com.precocerto.backend.infrastructure.repository.InsumosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsumosService {
    private final InsumosRepository repository;
    private final InsumosConverter converter;

    public InsumosDTOResponse adicionarInsumo(InsumosDTORequest dto) {
        if (repository.existsByNomeInsumo(dto.nomeInsumo())) {
            throw new ConflictException("Insumo com esse nome já cadastrado");
        }
        InsumosEntity entity = converter.paraEntity(dto);
        InsumosEntity salvo = repository.save(entity);
        return converter.paraDTO(salvo);
    }

    public List<InsumosDTOResponse> listarInsumos() {
        return repository.findAll()
                .stream() //Para abrir o fluxo de dados
                .map(converter::paraDTO) //Convertendo cada item
                .collect(Collectors.toList()); //rejunta em uma lista
    }

    public InsumosDTOResponse buscarUmInsumo(Long id){
        return repository.findById(id).map(converter::paraDTO).orElseThrow(()-> new RuntimeException("Insumo não encontrado"));
    }

    public InsumosDTOResponse atualizarInsumo(Long id, InsumosDTORequest dto){
        InsumosEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Insumo não encontrado"));
        if (dto.nomeInsumo() != null){
            entity.setNomeInsumo(dto.nomeInsumo());
        }
        if (dto.unidadeMedida() != null){
            entity.setUnidadeMedida(dto.unidadeMedida());
        }
        return converter.paraDTO(repository.save(entity));
    }

    public void excluirInsumo (Long id){
        buscarUmInsumo(id);
        repository.deleteById(id);
    }
}
