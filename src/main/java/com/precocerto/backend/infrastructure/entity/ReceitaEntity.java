package com.precocerto.backend.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "receita")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ReceitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeReceita;

    private Double tempoGas;

    private Double tempoEnergia;

    private Double custoTotal;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemReceitaEntity> itensReceita;
}
