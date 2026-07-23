package com.precocerto.backend.controller;

import com.precocerto.backend.dto.request.CustosFixosDTORequest;
import com.precocerto.backend.dto.response.CustosFixosDTOResponse;
import com.precocerto.backend.service.CustosFixosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/custos")
@CrossOrigin(origins = "http://localhost:5173")
public class CustosFixosController {
    private final CustosFixosService service;

    @PostMapping
    public ResponseEntity<CustosFixosDTOResponse> adicionarCustos(@RequestBody CustosFixosDTORequest dto){
        return ResponseEntity.ok(service.adicionarCustos(dto));
    }

    @GetMapping
    public ResponseEntity<List<CustosFixosDTOResponse>> listarCustos(){
        return ResponseEntity.ok(service.listarCustos());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<CustosFixosDTOResponse> buscarCustos(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarCustos(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustosFixosDTOResponse> atualizarCustos(@PathVariable Long id, @RequestBody CustosFixosDTORequest dto){
        return ResponseEntity.ok(service.atualizarCustos(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCustos(@PathVariable Long id){
        service.excluirCustos(id);
        return ResponseEntity.noContent().build();
    }
}
