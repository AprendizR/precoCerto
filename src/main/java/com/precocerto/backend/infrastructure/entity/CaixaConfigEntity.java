package com.precocerto.backend.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "caixa_config")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaixaConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private Double capitalInicial = 0.0;

    private LocalDateTime dataAtualizacao;

    @PrePersist
    @PreUpdate
    private void atualizarData() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
