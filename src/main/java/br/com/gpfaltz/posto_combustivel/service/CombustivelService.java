package br.com.gpfaltz.posto_combustivel.service;

import br.com.gpfaltz.posto_combustivel.dto.request.CombustivelRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.CombustivelResponse;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;
import br.com.gpfaltz.posto_combustivel.exception.ResourceNotFoundException;
import br.com.gpfaltz.posto_combustivel.repository.BombaRepository;
import br.com.gpfaltz.posto_combustivel.repository.CombustivelRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CombustivelService {
	
    private final CombustivelRepository repository;
    private final BombaRepository bombaRepository;
    private final ModelMapper mapper = new ModelMapper();

    public CombustivelService(CombustivelRepository repository, BombaRepository bombaRepository) {
        this.repository = repository;
        this.bombaRepository = bombaRepository;
    }

    public CombustivelResponse create(CombustivelRequest request) {
        Combustivel entity = mapper.map(request, Combustivel.class);
        Combustivel saved = repository.save(entity);
        return mapper.map(saved, CombustivelResponse.class);
    }

    public List<CombustivelResponse> getAll() {
        return repository.findAll().stream()
                .map(c -> mapper.map(c, CombustivelResponse.class))
                .collect(Collectors.toList());
    }

    public CombustivelResponse getById(Long id) {
        Combustivel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combustível não encontrado"));
        return mapper.map(entity, CombustivelResponse.class);
    }

    public CombustivelResponse update(Long id, CombustivelRequest request) {
        Combustivel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combustível não encontrado"));
        entity.setNome(request.getNome());
        entity.setPrecoPorLitro(request.getPrecoPorLitro());
        Combustivel saved = repository.save(entity);
        return mapper.map(saved, CombustivelResponse.class);
    }

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