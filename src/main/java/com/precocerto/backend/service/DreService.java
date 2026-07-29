package com.precocerto.backend.service;

import com.precocerto.backend.dto.response.DreResumoDTOResponse;
import com.precocerto.backend.enums.StatusVenda;
import com.precocerto.backend.infrastructure.entity.ItemReceitaEntity;
import com.precocerto.backend.infrastructure.entity.ReceitaEntity;
import com.precocerto.backend.infrastructure.entity.VendaEntity;
import com.precocerto.backend.infrastructure.repository.CompraRepository;
import com.precocerto.backend.infrastructure.repository.VendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DreService {
    private final VendaRepository vendaRepository;
    private final CompraRepository compraRepository;
    private final CaixaConfigService caixaConfigService;

    @Transactional
    public DreResumoDTOResponse buscarResumo() {
        List<VendaEntity> vendas = vendaRepository.findAllByStatusWithReceitaItens(StatusVenda.CONCLUIDA);

        double receitaBruta = vendas.stream()
                .mapToDouble(venda -> valorOuZero(venda.getPrecoVenda()))
                .sum();

        double cmvInsumos = vendas.stream()
                .mapToDouble(this::calcularCustoInsumos)
                .sum();

        double custosOperacionais = vendas.stream()
                .mapToDouble(this::calcularCustosOperacionais)
                .sum();

        double lucroOperacional = receitaBruta - cmvInsumos - custosOperacionais;
        double capitalInicial = caixaConfigService.buscarCapitalInicial();
        double totalCompras = valorOuZero(compraRepository.somarValorTotal());
        double saldoDisponivel = capitalInicial + receitaBruta - totalCompras;

        return DreResumoDTOResponse.builder()
                .receitaBruta(arredondar(receitaBruta))
                .cmvInsumos(arredondar(cmvInsumos))
                .despesasFixas(arredondar(custosOperacionais))
                .lucroOperacional(arredondar(lucroOperacional))
                .capitalInicial(arredondar(capitalInicial))
                .totalCompras(arredondar(totalCompras))
                .saldoDisponivel(arredondar(saldoDisponivel))
                .vendasConcluidas((long) vendas.size())
                .build();
    }

    private double calcularCustoInsumos(VendaEntity venda) {
        ReceitaEntity receita = venda.getReceita();
        if (receita == null || receita.getItensReceita() == null) {
            return 0.0;
        }

        return receita.getItensReceita().stream()
                .mapToDouble(this::calcularCustoItem)
                .sum();
    }

    private double calcularCustoItem(ItemReceitaEntity item) {
        if (item.getInsumos() == null) {
            return 0.0;
        }
        return valorOuZero(item.getQuantidadeUsada()) * valorOuZero(item.getInsumos().getCustoMedioUnitario());
    }

    private double calcularCustosOperacionais(VendaEntity venda) {
        ReceitaEntity receita = venda.getReceita();
        if (receita == null) {
            return 0.0;
        }

        double custoTotal = valorOuZero(receita.getCustoTotal());
        double custoInsumos = calcularCustoInsumos(venda);
        return Math.max(0.0, custoTotal - custoInsumos);
    }

    private double valorOuZero(Double valor) {
        return valor == null ? 0.0 : valor;
    }

    private double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
