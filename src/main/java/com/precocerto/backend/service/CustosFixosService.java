package com.precocerto.backend.service;

import com.precocerto.backend.converter.CustosFixosConverter;
import com.precocerto.backend.dto.request.CustosFixosDTORequest;
import com.precocerto.backend.dto.response.CustosFixosDTOResponse;
import com.precocerto.backend.infrastructure.entity.CustosFixosEntity;
import com.precocerto.backend.infrastructure.exception.ConflictException;
import com.precocerto.backend.infrastructure.repository.CustosFixosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustosFixosService {
    private final CustosFixosRepository repository;
    private final CustosFixosConverter converter;

    public CustosFixosDTOResponse adicionarCustos(CustosFixosDTORequest dto) {
        CustosFixosEntity custosFixos = converter.paraEntity(dto);
        CustosFixosEntity salvo = repository.save(custosFixos);
        return converter.paraDTO(salvo);
    }

    public List<CustosFixosDTOResponse> listarCustos() {
        return repository.findAll()
                .stream() //Para abrir o fluxo de dados
                .map(converter::paraDTO) //Convertendo cada item
                .collect(Collectors.toList()); //rejunta em uma lista
    }

    public CustosFixosDTOResponse buscarCustos(Long id) {
        return repository.findById(id).map(converter::paraDTO).orElseThrow(() -> new ConflictException("Custos não encontrados"));
    }

    public CustosFixosDTOResponse atualizarCustos(Long id, CustosFixosDTORequest dto) {
        CustosFixosEntity custosFixos = repository.findById(id).orElseThrow(() -> new ConflictException("Custos não encontrados"));
        if (dto.custoPorMinutoGas() != null) {
            custosFixos.setCustoPorMinutoGas(dto.custoPorMinutoGas());
        }
        if (dto.custoPorMinutoEnergia() != null) {
            custosFixos.setCustoPorMinutoEnergia(dto.custoPorMinutoEnergia());
        }
        return converter.paraDTO(repository.save(custosFixos));
    }

    public void excluirCustos(Long id){
       buscarCustos(id);
        repository.deleteById(id);
    }
}
