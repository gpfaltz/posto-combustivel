package br.com.gpfaltz.posto_combustivel.controller;

import br.com.gpfaltz.posto_combustivel.dto.request.AbastecimentoRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.AbastecimentoResponse;
import br.com.gpfaltz.posto_combustivel.service.AbastecimentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST para gerenciamento de abastecimentos.
 *
 * Endpoints disponíveis:
 * <ul>
 *   <li>POST   /api/abastecimentos</li>
 *   <li>GET    /api/abastecimentos</li>
 *   <li>GET    /api/abastecimentos/{id}</li>
 *   <li>PUT    /api/abastecimentos/{id}</li>
 *   <li>DELETE /api/abastecimentos/{id}</li>
 * </ul>
 *
 * @example
 * curl -X POST http://localhost:8080/api/abastecimentos \
 *      -H "Content-Type: application/json" \
 *      -d '{"bombaId":1,"data":"2023-01-01","volume":50.0}'
 */
@RestController
@RequestMapping("/api/abastecimentos")
public class AbastecimentoController {
	
    private final AbastecimentoService service;

    public AbastecimentoController(AbastecimentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AbastecimentoResponse> create(@RequestBody @Valid AbastecimentoRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<AbastecimentoResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbastecimentoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbastecimentoResponse> update(@PathVariable Long id, @RequestBody @Valid AbastecimentoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}