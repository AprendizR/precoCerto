package com.precocerto.backend.infrastructure.repository;

import com.precocerto.backend.infrastructure.entity.InsumosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsumosRepository extends JpaRepository<InsumosEntity, Long> {
    boolean existsByNomeInsumo(String nome);
}
