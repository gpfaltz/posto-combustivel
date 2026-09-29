package br.com.gpfaltz.posto_combustivel.controller;

import br.com.gpfaltz.posto_combustivel.dto.request.CombustivelRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.CombustivelResponse;
import br.com.gpfaltz.posto_combustivel.service.CombustivelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combustiveis")
public class CombustivelController {
    private final CombustivelService service;

    public CombustivelController(CombustivelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CombustivelResponse> create(@RequestBody @Valid CombustivelRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CombustivelResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CombustivelResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CombustivelResponse> update(@PathVariable Long id, @RequestBody @Valid CombustivelRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}