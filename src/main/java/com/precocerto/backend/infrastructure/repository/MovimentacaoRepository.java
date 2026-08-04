package com.precocerto.backend.infrastructure.repository;

import com.precocerto.backend.infrastructure.entity.MovimentacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {
    @Query("SELECT m FROM MovimentacaoEntity m LEFT JOIN FETCH m.insumos LEFT JOIN FETCH m.compra ORDER BY m.dataMovimentacao DESC, m.id DESC")
    List<MovimentacaoEntity> findAllWithInsumos();

    @Query("SELECT m FROM MovimentacaoEntity m LEFT JOIN FETCH m.insumos LEFT JOIN FETCH m.compra WHERE m.id = :id")
    Optional<MovimentacaoEntity> findByIdWithInsumos(Long id);

    List<MovimentacaoEntity> findByVendaId(Long vendaId);
}
