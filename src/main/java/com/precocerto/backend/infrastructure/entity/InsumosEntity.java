package com.precocerto.backend.infrastructure.entity;

import com.precocerto.backend.enums.UnidadeMedida;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "insumos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsumosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeInsumo;

    private Double quantidadeAtual = 0.0;

    private UnidadeMedida unidadeMedida;

    private Double custoMedioUnitario = 0.0;

    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }
}
