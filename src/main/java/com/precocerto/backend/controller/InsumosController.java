package com.precocerto.backend.controller;

import com.precocerto.backend.dto.request.InsumosDTORequest;
import com.precocerto.backend.dto.response.InsumosDTOResponse;
import com.precocerto.backend.service.InsumosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/insumos")
@CrossOrigin(origins = "http://localhost:5173")
public class InsumosController {
    private final InsumosService service;

    @PostMapping
    public ResponseEntity<InsumosDTOResponse> adicionarInsumo (@RequestBody InsumosDTORequest dto){
        return ResponseEntity.ok(service.adicionarInsumo(dto));
    }

    @GetMapping
    public ResponseEntity<List<InsumosDTOResponse>> listarInsumos (){
        return ResponseEntity.ok(service.listarInsumos());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<InsumosDTOResponse> buscarUmInsumo(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarUmInsumo(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<InsumosDTOResponse> atualizarInsumo (@PathVariable Long id, @RequestBody InsumosDTORequest dto){
        return ResponseEntity.ok(service.atualizarInsumo(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarInsumo(@PathVariable Long id){
        service.excluirInsumo(id);
        return ResponseEntity.noContent().build();
    }

}
