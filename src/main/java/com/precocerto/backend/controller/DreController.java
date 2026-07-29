package com.precocerto.backend.controller;

import com.precocerto.backend.dto.response.DreResumoDTOResponse;
import com.precocerto.backend.service.DreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dre")
@CrossOrigin(origins = "http://localhost:5173")
public class DreController {
    private final DreService service;

    @GetMapping("/resumo")
    public ResponseEntity<DreResumoDTOResponse> buscarResumo() {
        return ResponseEntity.ok(service.buscarResumo());
    }
}
