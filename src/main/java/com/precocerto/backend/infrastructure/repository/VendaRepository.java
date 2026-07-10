package com.precocerto.backend.infrastructure.repository;

import com.precocerto.backend.infrastructure.entity.VendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<VendaEntity, Long> {
}
