package com.precocerto.backend.controller;

import com.precocerto.backend.dto.request.VendaDTORequest;
import com.precocerto.backend.dto.response.VendaDTOResponse;
import com.precocerto.backend.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/venda")
public class VendaController {
    private final VendaService service;

    @PostMapping
    public ResponseEntity<VendaDTOResponse> adicionarVenda(@RequestBody VendaDTORequest dto){
        return ResponseEntity.ok(service.adicionarVenda(dto));
    }

    @GetMapping
    public ResponseEntity<List<VendaDTOResponse>> listarVendas(){
        return ResponseEntity.ok(service.listarVendas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTOResponse> buscarVenda(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarVenda(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaDTOResponse> atualizarVenda(@PathVariable Long id, @RequestBody VendaDTORequest dto){
        return ResponseEntity.ok(service.atualizarVenda(id, dto));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<VendaDTOResponse>cancelarVenda(@PathVariable Long id){
        service.cancelarVenda(id);
        return ResponseEntity.noContent().build();
    }
}
