package com.precocerto.backend.controller;

import com.precocerto.backend.dto.request.MovimentacaoDTORequest;
import com.precocerto.backend.dto.response.MovimentacaoDTOResponse;
import com.precocerto.backend.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {
    private final MovimentacaoService service;

    @PostMapping
    public ResponseEntity<MovimentacaoDTOResponse> criarMovimentacao(@RequestBody MovimentacaoDTORequest dto){
        return ResponseEntity.ok(service.criarMovimentacao(dto));
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoDTOResponse>> listarMovimentacoes(){
        return ResponseEntity.ok(service.listarMovimentacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoDTOResponse> buscarMovimentacao(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarMovimentacao(id));
    }

    @PostMapping("/{id}/estornar")
    public ResponseEntity<Void> estornarMovimentacao(@PathVariable Long id) {
        service.estornarMovimentacao(id);
        return ResponseEntity.noContent().build();
    }
}
