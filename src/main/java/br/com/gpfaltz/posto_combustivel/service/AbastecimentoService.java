package br.com.gpfaltz.posto_combustivel.service;

import br.com.gpfaltz.posto_combustivel.dto.request.AbastecimentoRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.AbastecimentoResponse;
import br.com.gpfaltz.posto_combustivel.entity.Abastecimento;
import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.exception.ResourceNotFoundException;
import br.com.gpfaltz.posto_combustivel.repository.AbastecimentoRepository;
import br.com.gpfaltz.posto_combustivel.repository.BombaRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

/**
 * Serviço de negócio para operações de {@link Abastecimento}.
 * Responsável por criar, consultar, atualizar e remover registros de abastecimento,
 * calculando o valor total (preço por litro * volume).
 */
@Service
public class AbastecimentoService {

    private final AbastecimentoRepository abastecimentoRepo;
    private final BombaRepository bombaRepo;

    public AbastecimentoService(AbastecimentoRepository abastecimentoRepo, BombaRepository bombaRepo) {
        this.abastecimentoRepo = abastecimentoRepo;
        this.bombaRepo = bombaRepo;
    }

    /**
     * Cria um novo registro de abastecimento.
     *
     * @param request DTO contendo ID da bomba, data e volume
     * @return DTO de resposta com detalhes da bomba, preço por litro e valor total
     * @throws ResourceNotFoundException se a bomba informada não existir
     */
    public AbastecimentoResponse create(AbastecimentoRequest request) {
        Bomba bomba = bombaRepo.findById(request.bombaId())
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrado"));
        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setBomba(bomba);
        abastecimento.setData(request.data());
        abastecimento.setVolume(request.volume());
        // Calculate total value = price per liter * volume
        BigDecimal total = bomba.getCombustivel().getPrecoPorLitro().multiply(request.volume());
        abastecimento.setValorTotal(total);
        Abastecimento saved = abastecimentoRepo.save(abastecimento);
        return new AbastecimentoResponse(
                saved.getId(),
                bomba.getId(),
                bomba.getNome(),
                saved.getData(),
                bomba.getCombustivel().getPrecoPorLitro(),
                saved.getVolume(),
                saved.getValorTotal()
        );
    }

    /**
     * Recupera todos os registros de abastecimento.
     *
     * @return lista de DTOs de resposta contendo informações da bomba e preço por litro
     */
    public List<AbastecimentoResponse> getAll() {
        return abastecimentoRepo.findAll().stream()
                .map(a -> {
                    Bomba b = a.getBomba();
                    return new AbastecimentoResponse(
                            a.getId(),
                            b.getId(),
                            b.getNome(),
                            a.getData(),
                            b.getCombustivel().getPrecoPorLitro(),
                            a.getVolume(),
                            a.getValorTotal()
                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * Busca um registro de abastecimento pelo seu identificador.
     *
     * @param id identificador do registro
     * @return DTO de resposta completo
     * @throws ResourceNotFoundException se o registro não for encontrado
     */
    public AbastecimentoResponse getById(Long id) {
        Abastecimento a = abastecimentoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Abastecimento não encontrado"));
        Bomba b = a.getBomba();
        return new AbastecimentoResponse(
                a.getId(),
                b.getId(),
                b.getNome(),
                a.getData(),
                b.getCombustivel().getPrecoPorLitro(),
                a.getVolume(),
                a.getValorTotal()
        );
    }

    /**
     * Atualiza um registro de abastecimento existente.
     *
     * @param id      identificador do registro a ser atualizado
     * @param request DTO contendo novos valores
     * @return DTO de resposta com os dados atualizados
     * @throws ResourceNotFoundException se a bomba ou o registro não existirem
     */
    public AbastecimentoResponse update(Long id, AbastecimentoRequest request) {
        Abastecimento a = abastecimentoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Abastecimento não encontrado"));
        Bomba bomba = bombaRepo.findById(request.bombaId())
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrado"));
        a.setBomba(bomba);
        a.setData(request.data());
        a.setVolume(request.volume());
        // Recalculate total value based on possibly changed volume or bomba
        BigDecimal total = bomba.getCombustivel().getPrecoPorLitro().multiply(request.volume());
        a.setValorTotal(total);
        Abastecimento saved = abastecimentoRepo.save(a);
        return new AbastecimentoResponse(
                saved.getId(),
                bomba.getId(),
                bomba.getNome(),
                saved.getData(),
                bomba.getCombustivel().getPrecoPorLitro(),
                saved.getVolume(),
                saved.getValorTotal()
        );
    }

    /**
     * Remove um registro de abastecimento.
     *
     * @param id identificador do registro a ser removido
     * @throws ResourceNotFoundException se o registro não existir
     */
    public void delete(Long id) {
        if (!abastecimentoRepo.existsById(id)) {
            throw new ResourceNotFoundException("Abastecimento não encontrado");
        }
        abastecimentoRepo.deleteById(id);
    }
}