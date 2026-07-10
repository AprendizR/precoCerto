package com.precocerto.backend.infrastructure.entity;

import com.precocerto.backend.enums.Tipo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimentacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "insumos_id")
    private InsumosEntity insumos;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private VendaEntity venda;

    private Tipo tipo;

    private Double quantidade;

    private Double precoCompra;

    private LocalDateTime dataMovimentacao;

    @PrePersist
    public void prePersist() {
        this.dataMovimentacao = LocalDateTime.now();
    }
}
