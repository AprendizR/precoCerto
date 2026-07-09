package com.precocerto.backend.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_receita")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ItemReceitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "insumos_id")
    private InsumosEntity insumos;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private ReceitaEntity receita;

    private Double quantidadeUsada;
}
