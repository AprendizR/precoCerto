package com.precocerto.backend.service;

import com.precocerto.backend.converter.ReceitaConverter;
import com.precocerto.backend.dto.request.ItemReceitaDTORequest;
import com.precocerto.backend.dto.request.ReceitaDTORequest;
import com.precocerto.backend.dto.response.ReceitaDTOResponse;
import com.precocerto.backend.infrastructure.entity.CustosFixosEntity;
import com.precocerto.backend.infrastructure.entity.InsumosEntity;
import com.precocerto.backend.infrastructure.entity.ItemReceitaEntity;
import com.precocerto.backend.infrastructure.entity.ReceitaEntity;
import com.precocerto.backend.infrastructure.repository.CustosFixosRepository;
import com.precocerto.backend.infrastructure.repository.InsumosRepository;
import com.precocerto.backend.infrastructure.repository.ReceitaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceitaService {
    private final ReceitaRepository repository;
    private final ReceitaConverter converter;
    private final CustosFixosRepository custosFixosRepository;
    private final InsumosRepository insumosRepository;

    @Transactional
    public ReceitaDTOResponse adicionarReceita(ReceitaDTORequest dto) {
        ReceitaEntity entity = converter.paraEntity(dto);
        entity.setItensReceita(criarItensReceita(entity, dto.itensReceita()));
        calcularCusto(entity);
        return converter.paraDTO(repository.save(entity));
    }

    @Transactional
    public List<ReceitaDTOResponse> listarReceitas() {
        return repository.findAll()
                .stream()
                .map(converter::paraDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReceitaDTOResponse buscarUmaReceita(Long id) {
        return repository.findById(id).map(converter::paraDTO).orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    @Transactional
    public ReceitaDTOResponse atualizarReceita(Long id, ReceitaDTORequest dto) {
        ReceitaEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Receita não encontrada"));
        if (dto.nomeReceita() != null) entity.setNomeReceita(dto.nomeReceita());
        if (dto.tempoGas() != null) entity.setTempoGas(dto.tempoGas());
        if (dto.tempoEnergia() != null) entity.setTempoEnergia(dto.tempoEnergia());
        if (dto.itensReceita() != null) {
            entity.getItensReceita().clear();
            List<ItemReceitaEntity> novosItens = criarItensReceita(entity, dto.itensReceita());
            entity.getItensReceita().addAll(novosItens);
        }
        calcularCusto(entity);
        return converter.paraDTO(repository.save(entity));

    }

    @Transactional
    public void excluirReceita(Long id) {
        buscarUmaReceita(id);
        repository.deleteById(id);
    }

    private void calcularCusto(ReceitaEntity entity) {
        CustosFixosEntity custos = custosFixosRepository.findAll().stream().findFirst().orElseThrow(() ->
                new RuntimeException("Custos fixos não configurados"));
        Double custoGas = entity.getTempoGas() * custos.getCustoPorMinutoGas();
        Double custoEnergia = entity.getTempoEnergia() * custos.getCustoPorMinutoEnergia();

        Double custoIngredientes = entity.getItensReceita().stream()
                .mapToDouble(item -> item.getQuantidadeUsada() * item.getInsumos().getCustoMedioUnitario())
                .sum();

        entity.setCustoTotal(custoGas + custoEnergia + custoIngredientes);
    }

    private List<ItemReceitaEntity> criarItensReceita(ReceitaEntity entity, List<ItemReceitaDTORequest> itensDto) {
        return itensDto.stream().map(itemDto -> {
            InsumosEntity insumo = insumosRepository.findById(itemDto.insumoId()).orElseThrow(() ->
                    new RuntimeException("Insumo não encontrado: " + itemDto.insumoId()));

            return ItemReceitaEntity.builder()
                    .receita(entity)
                    .insumos(insumo)
                    .quantidadeUsada(itemDto.quantidadeUsada())
                    .build();
        }).collect(Collectors.toList());
    }
}

