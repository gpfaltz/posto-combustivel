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

/**
 * Serviço de negócio para operações relacionadas a {@link Bomba}.
 * Gerencia criação, consulta, atualização e remoção de bombas,
 * garantindo integridade referencial com {@link Combustivel} e
 * {@link Abastecimento}.
 */
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

    /**
     * Cria uma nova bomba associada a um combustível existente.
     *
     * @param request DTO contendo nome da bomba e ID do combustível
     * @return DTO de resposta com dados da bomba criada e informações do combustível
     * @throws ResourceNotFoundException se o combustível informado não existir
     */
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

    /**
     * Recupera todas as bombas cadastradas.
     *
     * @return lista de DTOs de resposta contendo dados da bomba e do combustível associado
     */
    public List<BombaResponse> getAll() {
        return bombaRepo.findAll().stream().map(b -> {
            BombaResponse r = mapper.map(b, BombaResponse.class);
            r.setCombustivelNome(b.getCombustivel().getNome());
            r.setPrecoPorLitro(b.getCombustivel().getPrecoPorLitro());
            return r;
        }).collect(Collectors.toList());
    }

    /**
     * Busca uma bomba pelo seu identificador.
     *
     * @param id identificador da bomba
     * @return DTO de resposta com dados da bomba e do combustível
     * @throws ResourceNotFoundException se a bomba não for encontrada
     */
    public BombaResponse getById(Long id) {
        Bomba bomba = bombaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrado"));
        BombaResponse resp = mapper.map(bomba, BombaResponse.class);
        resp.setCombustivelNome(bomba.getCombustivel().getNome());
        resp.setPrecoPorLitro(bomba.getCombustivel().getPrecoPorLitro());
        return resp;
    }

    /**
     * Atualiza os dados de uma bomba existente.
     *
     * @param id      identificador da bomba a ser atualizada
     * @param request DTO contendo novo nome e ID do combustível
     * @return DTO de resposta com os dados atualizados
     * @throws ResourceNotFoundException se a bomba ou o combustível não existirem
     */
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

    /**
     * Remove uma bomba, impedindo exclusão caso existam abastecimentos associados.
     *
     * @param id identificador da bomba a ser removida
     * @throws ResourceNotFoundException se a bomba não existir
     * @throws IllegalStateException    se houver abastecimentos vinculados à bomba
     */
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