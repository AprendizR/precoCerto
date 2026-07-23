package com.precocerto.backend.infrastructure.repository;

import com.precocerto.backend.infrastructure.entity.ReceitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<ReceitaEntity, Long> {
}
