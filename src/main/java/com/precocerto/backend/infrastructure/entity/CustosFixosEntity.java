package com.precocerto.backend.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "custos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustosFixosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double custoPorMinutoGas;

    private Double custoPorMinutoEnergia;
}
