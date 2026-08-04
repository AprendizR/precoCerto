package com.precocerto.backend.infrastructure.repository;

import com.precocerto.backend.infrastructure.entity.CompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompraRepository extends JpaRepository<CompraEntity, Long> {
    @Query("SELECT c FROM CompraEntity c LEFT JOIN FETCH c.insumo LEFT JOIN FETCH c.movimentacao ORDER BY c.dataCompra DESC, c.id DESC")
    List<CompraEntity> findAllWithInsumo();

    @Query("SELECT COALESCE(SUM(c.valorTotal), 0.0) FROM CompraEntity c")
    Double somarValorTotal();
}
