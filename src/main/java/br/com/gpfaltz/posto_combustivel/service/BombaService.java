package br.com.gpfaltz.posto_combustivel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.gpfaltz.posto_combustivel.dto.request.BombaRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.BombaResponse;
import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;
import br.com.gpfaltz.posto_combustivel.exception.ResourceNotFoundException;
import br.com.gpfaltz.posto_combustivel.repository.AbastecimentoRepository;
import br.com.gpfaltz.posto_combustivel.repository.BombaRepository;
import br.com.gpfaltz.posto_combustivel.repository.CombustivelRepository;

@Service
public class BombaService {

	private final BombaRepository bombaRepo;
	private final CombustivelRepository combustivelRepo;
	private final AbastecimentoRepository abastecimentoRepo;
	private final ModelMapper mapper = new ModelMapper();

	public BombaService(BombaRepository bombaRepo, CombustivelRepository combustivelRepo,
			AbastecimentoRepository abastecimentoRepo) {
		this.bombaRepo = bombaRepo;
		this.combustivelRepo = combustivelRepo;
		this.abastecimentoRepo = abastecimentoRepo;
	}

	public BombaResponse create(BombaRequest request) {
		Combustivel combustivel = combustivelRepo.findById(request.getCombustivelId())
				.orElseThrow(() -> new ResourceNotFoundException("Combustível não encontrado"));
		Bomba bomba = new Bomba();
		bomba.setNome(request.getNome());
		bomba.setCombustivel(combustivel);
		Bomba saved = bombaRepo.save(bomba);
		BombaResponse resp = mapper.map(saved, BombaResponse.class);
		resp.setCombustivelNome(combustivel.getNome());
		resp.setPrecoPorLitro(combustivel.getPrecoPorLitro());
		return resp;
	}

	public List<BombaResponse> getAll() {
		return bombaRepo.findAll().stream().map(b -> {
			BombaResponse r = mapper.map(b, BombaResponse.class);
			r.setCombustivelNome(b.getCombustivel().getNome());
			r.setPrecoPorLitro(b.getCombustivel().getPrecoPorLitro());
			return r;
		}).collect(Collectors.toList());
	}

	public BombaResponse getById(Long id) {
		Bomba bomba = bombaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrado"));
		BombaResponse resp = mapper.map(bomba, BombaResponse.class);
		resp.setCombustivelNome(bomba.getCombustivel().getNome());
		resp.setPrecoPorLitro(bomba.getCombustivel().getPrecoPorLitro());
		return resp;
	}

	public BombaResponse update(Long id, BombaRequest request) {
		Bomba bomba = bombaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrado"));
		Combustivel combustivel = combustivelRepo.findById(request.getCombustivelId())
				.orElseThrow(() -> new ResourceNotFoundException("Combustível não encontrado"));
		bomba.setNome(request.getNome());
		bomba.setCombustivel(combustivel);
		Bomba saved = bombaRepo.save(bomba);
		BombaResponse resp = mapper.map(saved, BombaResponse.class);
		resp.setCombustivelNome(combustivel.getNome());
		resp.setPrecoPorLitro(combustivel.getPrecoPorLitro());
		return resp;
	}

	public void delete(Long id) {
		if (!bombaRepo.existsById(id)) {
			throw new ResourceNotFoundException("Bomba não encontrado");
		}
		// Previne deleção se algum Abastecimento referenciar esta Bomba
		boolean hasAbastecimento = abastecimentoRepo.existsByBombaId(id);
		if (hasAbastecimento) {
			throw new IllegalStateException("Não é possível excluir Bomba porque há Abastecimento(s) associado(s).");
		}
		// Procede com a exclusão da Bomba
		bombaRepo.deleteById(id);
	}
}