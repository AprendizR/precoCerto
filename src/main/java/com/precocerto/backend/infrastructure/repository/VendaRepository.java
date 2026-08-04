package com.precocerto.backend.infrastructure.repository;

import com.precocerto.backend.enums.StatusVenda;
import com.precocerto.backend.infrastructure.entity.VendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VendaRepository extends JpaRepository<VendaEntity, Long> {
    @Query("""
            SELECT DISTINCT v FROM VendaEntity v
            LEFT JOIN FETCH v.receita r
            LEFT JOIN FETCH r.itensReceita i
            LEFT JOIN FETCH i.insumos
            WHERE v.status = :status
            """)
    List<VendaEntity> findAllByStatusWithReceitaItens(@Param("status") StatusVenda status);
}
