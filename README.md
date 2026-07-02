# Preço Certo — Precificação, Custos e DRE para Microempreendedores



**Preço Certo** é uma aplicação (web/mobile) para ajudar **microempreendedores** a entenderem, registrarem e controlarem **custos, despesas, vendas e lucro**, com foco em **precificação correta** e geração de **DRE (Demonstrativo de Resultado do Exercício)**.



Problema a resolver: Falta de precisão nos custos e margem de lucro em microempresas



---



## 📋 Sobre o Projeto



Este projeto está sendo desenvolvido com foco em **excelência técnica**, **clareza de regras de negócio** e **entregas incrementais**, guiado por levantamento contínuo de requisitos e validação das necessidades reais do público-alvo.



- Documentação e planejamento: Notion (requisitos, regras de negócio, modelagem e backlog)

- Evolução por fases (MVP → fases seguintes)

- Cálculos financeiros com precisão e rastreabilidade



---



## ✨ Funcionalidades (escopo atual)



### Requisitos funcionais

- **RF-01 (CRUD de Insumos):** Cadastro de insumos com unidade de medida (KG, G, ML, UN) e controle de estoque.
- **RF-02 (CRUD de Receitas):** Composição de produtos através de ItemReceita (insumos + quantidades).
- **RF-03 (CRUD de Movimentação):** Registro de entradas (compras) e saídas (consumo/perda) para controle de estoque e custo médio.
- **RF-04 (CRUD de Vendas):** Registro de vendas que dispara automaticamente a baixa de estoque baseada na Receita.
- **RF-05 (CRUD de Custos Fixos):** Cadastro de parâmetros operacionais (custo de energia/gás por minuto e despesas fixas como aluguel/pro-labore).
- **RF-06 (Cálculo Automatizado):** O sistema deve calcular o custo direto (insumos) e o custo indireto (rateio) de cada produto.
- **RF-07 (Relatório DRE):** Gerar visão contábil: *Receita Bruta - Custos Diretos (CPV) - Despesas Fixas = Lucro Líquido*.



### Regras de negócio importantes

- **RN-01 (Cálculo do Custo Total):** Custo Total = Soma(Insumos) + Soma(Gás/Energia por tempo) + Rateio(Despesas Fixas).
- **RN-02 (Precificação):** O preço de venda sugerido deve aplicar um markup sobre o Custo Total definido pelo usuário.
- **RN-03 (Estoque e Custo Médio):** A cada ENTRADA de insumo, o Custo Médio Unitári` deve ser recalculado via média ponderada.
- **RN-04 (Rastreabilidade):** Toda alteração de estoque deve ser registrada via Movimentacao, criando um histórico auditável.
- **RN-06 (Custo Dinâmico):** O custo das receitas é calculado de forma dinâmica no momento da consulta, utilizando o custoMedioUnitario atual de cada insumo no estoque



---



## 🛠 Metodologia de Trabalho



- **Levantamento de Requisitos:** entrevistas e documentação contínua no Notion.

- **Modelagem:** diagrama de classes + regras de negócio como fonte de verdade.

- **Gestão de Tarefas:** backlog (Kanban) para acompanhar mini tasks e entregas.

- **DevOps (CI/CD):** via GitHub.



---



## 🚀 Tecnologias Utilizadas



- **Java 21**

- **Spring Boot 4.1**

- **Maven**

- **React 19** (frontend)

- **PostgreSQL 17** (banco de dados)

- **GitHub** (CI/CD)



---



### A modelagem e o diagrama de classes estão sendo mantidos no Notion.



---



## 🚦 Status do Projeto



- [✅] Requisitos levantados

- [✅] Modelagem de classes finalizada

- [✅] Setup do ambiente backend

- [ ] Setup do ambiente front

- [✅] Banco de dados

- [ ] Pipeline de CI/CD configurado

- [ ] Desenvolvimento do MVP (insumos + produtos + precificação)

- [ ] Fase 2 (despesas fixas + rateio)

- [ ] Fase 3 (DRE + exportações)



---



## 🧭 Roadmap (fases)



- **Fase 1 (Base Operacional):** Implementação das entidades Insumo, Movimentação e Receita (cálculo de custo direto).

- **Fase 2 (Custos de Produção):** Implementação de CustosFixos e integração no cálculo do CustoTotal.

- **Fase 3 (Visão Gerencial):** Implementação do DREService para cruzamento de dados de Venda vs Custos.



---



## 📜 Licença



Este projeto está licenciado sob a **MIT License** — veja o arquivo `LICENSE` para detalhes.
