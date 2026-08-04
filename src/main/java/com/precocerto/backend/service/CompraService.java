package com.precocerto.backend.service;

import com.precocerto.backend.converter.CompraConverter;
import com.precocerto.backend.dto.request.CompraDTORequest;
import com.precocerto.backend.dto.request.InsumoRefDTO;
import com.precocerto.backend.dto.request.MovimentacaoDTORequest;
import com.precocerto.backend.dto.response.CompraDTOResponse;
import com.precocerto.backend.dto.response.MovimentacaoDTOResponse;
import com.precocerto.backend.enums.Tipo;
import com.precocerto.backend.infrastructure.entity.CompraEntity;
import com.precocerto.backend.infrastructure.entity.InsumosEntity;
import com.precocerto.backend.infrastructure.entity.MovimentacaoEntity;
import com.precocerto.backend.infrastructure.repository.CompraRepository;
import com.precocerto.backend.infrastructure.repository.InsumosRepository;
import com.precocerto.backend.infrastructure.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraService {
    private final CompraRepository repository;
    private final InsumosRepository insumosRepository;
    private final MovimentacaoService movimentacaoService;
    private final MovimentacaoRepository movimentacaoRepository;
    private final CompraConverter converter;

    @Transactional
    public CompraDTOResponse registrarCompra(CompraDTORequest dto) {
        InsumosEntity insumo = insumosRepository.findById(dto.insumoId()).orElseThrow(() -> new RuntimeException("Insumo nao encontrado"));

        MovimentacaoDTORequest movimentacaoRequest = new MovimentacaoDTORequest(
                new InsumoRefDTO(insumo.getId()),
                Tipo.ENTRADA,
                dto.quantidade(),
                dto.valorTotal()
        );
        MovimentacaoDTOResponse movimentacaoSalvaDTO = movimentacaoService.criarMovimentacao(movimentacaoRequest);

        MovimentacaoEntity movimentacaoEntity = movimentacaoRepository.findById(movimentacaoSalvaDTO.id())
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar movimentação gerada"));

        CompraEntity compra = converter.paraEntity(dto, insumo);
        compra.setMovimentacao(movimentacaoEntity);

        if (dto.quantidade() > 0) {
            compra.setCustoUnitarioCompra(dto.valorTotal() / dto.quantidade());
        }

        CompraEntity salva = repository.save(compra);
        return converter.paraDTO(salva);
    }

    public List<CompraDTOResponse> listarCompras() {
        return repository.findAllWithInsumo()
                .stream()
                .map(converter::paraDTO)
                .collect(Collectors.toList());
    }
}