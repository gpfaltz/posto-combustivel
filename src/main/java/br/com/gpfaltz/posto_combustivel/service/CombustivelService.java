package br.com.gpfaltz.posto_combustivel.service;

import br.com.gpfaltz.posto_combustivel.dto.request.CombustivelRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.CombustivelResponse;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;
import br.com.gpfaltz.posto_combustivel.exception.ResourceNotFoundException;
import br.com.gpfaltz.posto_combustivel.repository.BombaRepository;
import br.com.gpfaltz.posto_combustivel.repository.CombustivelRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço de negócio para operações relacionadas a {@link Combustivel}.
 * Responsável por orquestrar persistência e regras de negócio.
 */
@Service
public class CombustivelService {
    
    private final CombustivelRepository repository;
    private final BombaRepository bombaRepository;

    public CombustivelService(CombustivelRepository repository, BombaRepository bombaRepository) {
        this.repository = repository;
        this.bombaRepository = bombaRepository;
    }

    /**
     * Cria um novo registro de combustível.
     *
     * @param request DTO contendo nome e preço por litro
     * @return DTO de resposta com os dados persistidos
     */
    public CombustivelResponse create(CombustivelRequest request) {
        // Manual mapping from request record to entity
        Combustivel entity = new Combustivel();
        entity.setNome(request.nome());
        entity.setPrecoPorLitro(request.precoPorLitro());
        Combustivel saved = repository.save(entity);
        return new CombustivelResponse(saved.getId(), saved.getNome(), saved.getPrecoPorLitro());
    }

    /**
     * Recupera todos os combustíveis cadastrados.
     *
     * @return lista de DTOs de resposta
     */
    public List<CombustivelResponse> getAll() {
        return repository.findAll().stream()
                .map(c -> new CombustivelResponse(c.getId(), c.getNome(), c.getPrecoPorLitro()))
                .collect(Collectors.toList());
    }

    /**
     * Busca um combustível pelo seu identificador.
     *
     * @param id identificador do combustível
     * @return DTO de resposta
     * @throws ResourceNotFoundException se o combustível não for encontrado
     */
    public CombustivelResponse getById(Long id) {
        Combustivel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combustível não encontrado"));
        return new CombustivelResponse(entity.getId(), entity.getNome(), entity.getPrecoPorLitro());
    }

    /**
     * Atualiza os dados de um combustível existente.
     *
     * @param id      identificador do combustível a ser atualizado
     * @param request DTO contendo os novos valores
     * @return DTO de resposta com os dados atualizados
     * @throws ResourceNotFoundException se o combustível não existir
     */
    public CombustivelResponse update(Long id, CombustivelRequest request) {
        Combustivel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combustível não encontrado"));
        entity.setNome(request.nome());
        entity.setPrecoPorLitro(request.precoPorLitro());
        Combustivel saved = repository.save(entity);
        return new CombustivelResponse(saved.getId(), saved.getNome(), saved.getPrecoPorLitro());
    }

    /**
     * Remove um combustível, verificando se há bombas associadas.
     *
     * @param id identificador do combustível a ser excluído
     * @throws ResourceNotFoundException se o combustível não existir
     * @throws IllegalStateException    se houver bombas associadas ao combustível
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Combustível não encontrado");
        }
        // Previne deleção se houver bombas associadas ao combustível
        boolean hasBomba = bombaRepository.existsByCombustivelId(id);
        if (hasBomba) {
            // Mensagem informando que a exclusão não é permitida
            throw new IllegalStateException("Não é possível excluir Combustível porque há Bomba(s) associada(s).");
        }
        // Procede com a exclusão do combustível
        repository.deleteById(id);
    }
}