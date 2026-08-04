package com.precocerto.backend.controller;

import com.precocerto.backend.dto.request.CaixaConfigDTORequest;
import com.precocerto.backend.dto.response.CaixaConfigDTOResponse;
import com.precocerto.backend.service.CaixaConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/caixa")
@CrossOrigin(origins = "http://localhost:5173")
public class CaixaConfigController {
    private final CaixaConfigService service;

    @GetMapping
    public ResponseEntity<CaixaConfigDTOResponse> buscarConfiguracao() {
        return ResponseEntity.ok(service.buscarConfiguracao());
    }

    @PutMapping
    public ResponseEntity<CaixaConfigDTOResponse> atualizarConfiguracao(@RequestBody CaixaConfigDTORequest dto) {
        return ResponseEntity.ok(service.atualizarConfiguracao(dto));
    }
}
