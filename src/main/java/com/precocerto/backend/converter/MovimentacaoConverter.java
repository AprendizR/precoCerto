package com.precocerto.backend.converter;

import com.precocerto.backend.dto.request.MovimentacaoDTORequest;
import com.precocerto.backend.dto.response.MovimentacaoDTOResponse;
import com.precocerto.backend.infrastructure.entity.MovimentacaoEntity;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoConverter {
    public MovimentacaoDTOResponse paraDTO(MovimentacaoEntity entity) {
        return MovimentacaoDTOResponse.builder()
                .id(entity.getId())
                .insumo(entity.getInsumos())
                .tipo(entity.getTipo())
                .quantidade(entity.getQuantidade())
                .precoCompra(entity.getPrecoCompra())
                .dataMovimentacao(entity.getDataMovimentacao())
                .build();
    }

    public MovimentacaoEntity paraEntity(MovimentacaoDTORequest dto) {
        return MovimentacaoEntity.builder()
                .tipo(dto.tipo())
                .quantidade(dto.quantidade())
                .precoCompra(dto.precoCompra())
                .build();
    }
}
