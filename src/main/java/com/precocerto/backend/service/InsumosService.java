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
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsumosService {
    private final InsumosRepository repository;
    private final InsumosConverter converter;

    public InsumosDTOResponse adicionarInsumo(InsumosDTORequest dto) {
        String nomeNormalizado = normalizarTexto(dto.nomeInsumo());
        if (repository.existsByNomeInsumoIgnoreCase(nomeNormalizado)) {
            throw new ConflictException("Insumo com esse nome ja cadastrado");
        }

        InsumosEntity entity = converter.paraEntity(dto);
        entity.setNomeInsumo(nomeNormalizado);
        entity.setCustoMedioUnitario(0.0);

        InsumosEntity salvo = repository.save(entity);
        return converter.paraDTO(salvo);
    }

    public List<InsumosDTOResponse> listarInsumos() {
        return repository.findAll()
                .stream()
                .map(converter::paraDTO)
                .collect(Collectors.toList());
    }

    public InsumosDTOResponse buscarUmInsumo(Long id) {
        return repository.findById(id)
                .map(converter::paraDTO)
                .orElseThrow(() -> new RuntimeException("Insumo nao encontrado"));
    }

    public InsumosDTOResponse atualizarInsumo(Long id, InsumosDTORequest dto) {
        InsumosEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo nao encontrado"));

        if (dto.nomeInsumo() != null) {
            String nomeNormalizado = normalizarTexto(dto.nomeInsumo());
            if (!nomeNormalizado.equalsIgnoreCase(entity.getNomeInsumo()) && repository.existsByNomeInsumoIgnoreCase(nomeNormalizado)) {
                throw new ConflictException("Insumo com esse nome ja cadastrado");
            }
            entity.setNomeInsumo(nomeNormalizado);
        }

        if (dto.unidadeMedida() != null) {
            entity.setUnidadeMedida(dto.unidadeMedida());
        }

        return converter.paraDTO(repository.save(entity));
    }

    public void excluirInsumo(Long id) {
        buscarUmInsumo(id);
        repository.deleteById(id);
    }

    private String normalizarTexto(String valor) {
        return valor == null ? null : valor.trim().toUpperCase(Locale.ROOT);
    }
}
