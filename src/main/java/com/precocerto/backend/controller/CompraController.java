package com.precocerto.backend.controller;

import com.precocerto.backend.dto.request.CompraDTORequest;
import com.precocerto.backend.dto.response.CompraDTOResponse;
import com.precocerto.backend.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compras")
@CrossOrigin(origins = "http://localhost:5173")
public class CompraController {
    private final CompraService service;

    @PostMapping
    public ResponseEntity<CompraDTOResponse> registrarCompra(@RequestBody CompraDTORequest dto) {
        return ResponseEntity.ok(service.registrarCompra(dto));
    }

    @GetMapping
    public ResponseEntity<List<CompraDTOResponse>> listarCompras() {
        return ResponseEntity.ok(service.listarCompras());
    }
}
