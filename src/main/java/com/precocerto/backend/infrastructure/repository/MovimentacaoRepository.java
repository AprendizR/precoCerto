package com.precocerto.backend.infrastructure.repository;

import com.precocerto.backend.infrastructure.entity.MovimentacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {
    @Query("SELECT m FROM MovimentacaoEntity m JOIN FETCH m.insumos")
    List<MovimentacaoEntity> findAllWithInsumos();

    @Query("SELECT m FROM MovimentacaoEntity m JOIN FETCH m.insumos WHERE m.id = :id")
    Optional<MovimentacaoEntity> findByIdWithInsumos(Long id);
}
