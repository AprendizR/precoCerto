package com.precocerto.backend.dto.request;

public record CompraDTORequest(
        Long insumoId,
        Double quantidade,
        Double valorTotal
) {
}
