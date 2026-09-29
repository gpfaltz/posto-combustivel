package br.com.gpfaltz.posto_combustivel.service;

import br.com.gpfaltz.posto_combustivel.dto.request.AbastecimentoRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.AbastecimentoResponse;
import br.com.gpfaltz.posto_combustivel.entity.Abastecimento;
import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.exception.ResourceNotFoundException;
import br.com.gpfaltz.posto_combustivel.repository.AbastecimentoRepository;
import br.com.gpfaltz.posto_combustivel.repository.BombaRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
public class AbastecimentoService {
	
    private final AbastecimentoRepository abastecimentoRepo;
    private final BombaRepository bombaRepo;
    private final ModelMapper mapper = new ModelMapper();

    public AbastecimentoService(AbastecimentoRepository abastecimentoRepo, BombaRepository bombaRepo) {
        this.abastecimentoRepo = abastecimentoRepo;
        this.bombaRepo = bombaRepo;
    }

    public AbastecimentoResponse create(AbastecimentoRequest request) {
        Bomba bomba = bombaRepo.findById(request.getBombaId())
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrado"));
        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setBomba(bomba);
        abastecimento.setData(request.getData());
        abastecimento.setVolume(request.getVolume());
        // Calculate total value = price per liter * volume
        BigDecimal total = bomba.getCombustivel().getPrecoPorLitro().multiply(request.getVolume());
        abastecimento.setValorTotal(total);
        Abastecimento saved = abastecimentoRepo.save(abastecimento);
        AbastecimentoResponse resp = mapper.map(saved, AbastecimentoResponse.class);
        resp.setBombaNome(bomba.getNome());
        resp.setBombaId(bomba.getId());
        // set price per liter in response
        resp.setPrecoPorLitro(bomba.getCombustivel().getPrecoPorLitro());
        return resp;
    }

    public List<AbastecimentoResponse> getAll() {
        return abastecimentoRepo.findAll().stream()
                .map(a -> {
                    AbastecimentoResponse r = mapper.map(a, AbastecimentoResponse.class);
                    Bomba b = a.getBomba();
                    r.setBombaNome(b.getNome());
                    r.setBombaId(b.getId());
                    // include price per liter in each response
                    r.setPrecoPorLitro(b.getCombustivel().getPrecoPorLitro());
                    return r;
                })
                .collect(Collectors.toList());
    }

    public AbastecimentoResponse getById(Long id) {
        Abastecimento a = abastecimentoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Abastecimento não encontrado"));
        AbastecimentoResponse r = mapper.map(a, AbastecimentoResponse.class);
        Bomba b = a.getBomba();
        r.setBombaNome(b.getNome());
        r.setBombaId(b.getId());
        // include price per liter in response
        r.setPrecoPorLitro(b.getCombustivel().getPrecoPorLitro());
        return r;
    }

    public AbastecimentoResponse update(Long id, AbastecimentoRequest request) {
        Abastecimento a = abastecimentoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Abastecimento não encontrado"));
        Bomba bomba = bombaRepo.findById(request.getBombaId())
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrado"));
        a.setBomba(bomba);
        a.setData(request.getData());
        a.setVolume(request.getVolume());
        // Recalculate total value based on possibly changed volume or bomba
        BigDecimal total = bomba.getCombustivel().getPrecoPorLitro().multiply(request.getVolume());
        a.setValorTotal(total);
        Abastecimento saved = abastecimentoRepo.save(a);
        AbastecimentoResponse r = mapper.map(saved, AbastecimentoResponse.class);
        r.setBombaNome(bomba.getNome());
        r.setBombaId(bomba.getId());
        return r;
    }

    public void delete(Long id) {
        if (!abastecimentoRepo.existsById(id)) {
            throw new ResourceNotFoundException("Abastecimento não encontrado");
        }
        abastecimentoRepo.deleteById(id);
    }
}