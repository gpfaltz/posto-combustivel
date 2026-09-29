package br.com.gpfaltz.posto_combustivel.controller;

import br.com.gpfaltz.posto_combustivel.dto.request.BombaRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.BombaResponse;
import br.com.gpfaltz.posto_combustivel.service.BombaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST para gerenciamento de bombas.
 *
 * Endpoints disponíveis:
 * <ul>
 *   <li>POST   /api/bombas</li>
 *   <li>GET    /api/bombas</li>
 *   <li>GET    /api/bombas/{id}</li>
 *   <li>PUT    /api/bombas/{id}</li>
 *   <li>DELETE /api/bombas/{id}</li>
 * </ul>
 *
 * @example
 * curl -X POST http://localhost:8080/api/bombas \
 *      -H "Content-Type: application/json" \
 *      -d '{"nome":"Bomba 1","combustivelId":1}'
 */
@RestController
@RequestMapping("/api/bombas")
public class BombaController {
    private final BombaService service;

    public BombaController(BombaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BombaResponse> create(@RequestBody @Valid BombaRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<BombaResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BombaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BombaResponse> update(@PathVariable Long id, @RequestBody @Valid BombaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}