package com.precocerto.backend.infrastructure.entity;

import com.precocerto.backend.enums.FormaPagamento;
import com.precocerto.backend.enums.StatusVenda;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "venda")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private ReceitaEntity receita;

    private FormaPagamento formaPagamento;

    private Double precoVenda;

    private LocalDateTime dataVenda;

    private StatusVenda status = StatusVenda.CONCLUIDA;

    @PrePersist
    private void prePersist(){
        this.dataVenda = LocalDateTime.now();
    }
}
