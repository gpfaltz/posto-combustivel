package br.com.gpfaltz.posto_combustivel.controller;

import br.com.gpfaltz.posto_combustivel.dto.request.CombustivelRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.CombustivelResponse;
import br.com.gpfaltz.posto_combustivel.service.CombustivelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de combustíveis.
 *
 * Endpoints disponíveis:
 * <ul>
 *   <li>POST   /api/combustiveis</li>
 *   <li>GET    /api/combustiveis</li>
 *   <li>GET    /api/combustiveis/{id}</li>
 *   <li>PUT    /api/combustiveis/{id}</li>
 *   <li>DELETE /api/combustiveis/{id}</li>
 * </ul>
 *
 * @example
 * curl -X POST http://localhost:8080/api/combustiveis \
 *      -H "Content-Type: application/json" \
 *      -d '{"nome":"Álcool","precoPorLitro":3.59}'
 */
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