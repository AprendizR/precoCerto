package com.precocerto.backend.controller;

import com.precocerto.backend.dto.request.ReceitaDTORequest;
import com.precocerto.backend.dto.response.ReceitaDTOResponse;
import com.precocerto.backend.service.ReceitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receita")
public class ReceitaController {
    private final ReceitaService service;

    @PostMapping
    public ResponseEntity<ReceitaDTOResponse> adicionarReceita(@RequestBody ReceitaDTORequest dto) {
        return ResponseEntity.ok(service.adicionarReceita(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReceitaDTOResponse>> listarReceitas() {
        return ResponseEntity.ok(service.listarReceitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDTOResponse> buscarUmaReceita(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarUmaReceita(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceitaDTOResponse> atualizarReceita(@PathVariable Long id, @RequestBody ReceitaDTORequest dto) {
        return ResponseEntity.ok(service.atualizarReceita(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long id) {
        service.excluirReceita(id);
        return ResponseEntity.noContent().build();
    }
}
