package br.com.gpfaltz.posto_combustivel.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.gpfaltz.posto_combustivel.entity.Abastecimento;
import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;

/**
 * Testes de integração para {@link AbastecimentoRepository}.
 * Verifica o método {@code existsByBombaId} que indica se há abastecimentos
 * associados a uma bomba específica.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=AbastecimentoRepositoryExistsTest
 * </pre>
 */
@DataJpaTest
class AbastecimentoRepositoryExistsTest {

    @Autowired
    private AbastecimentoRepository abastecimentoRepository;

    @Autowired
    private BombaRepository bombaRepository;

    @Autowired
    private CombustivelRepository combustivelRepository;

    /**
     * Verifica que {@code existsByBombaId} retorna <code>true</code> quando há
     * ao menos um registro de {@link Abastecimento} associado à bomba informada.
     */
    @Test
    void existsByBombaId_ShouldReturnTrueWhenAbastecimentoExists() {
        // arrange: create and persist related entities
        Combustivel combustivel = Combustivel.builder()
                .nome("Álcool")
                .precoPorLitro(new BigDecimal("3.50"))
                .build();
        Combustivel savedCombustivel = combustivelRepository.save(combustivel);

        Bomba bomba = Bomba.builder()
                .nome("Bomba 1")
                .combustivel(savedCombustivel)
                .build();
        Bomba savedBomba = bombaRepository.save(bomba);

        Abastecimento abastecimento = Abastecimento.builder()
                .bomba(savedBomba)
                .data(LocalDate.now())
                .volume(new BigDecimal("10"))
                .valorTotal(new BigDecimal("35.00"))
                .build();
        abastecimentoRepository.save(abastecimento);

        assertTrue(abastecimentoRepository.existsByBombaId(savedBomba.getId()));
    }

    /**
     * Verifica que {@code existsByBombaId} retorna <code>false</code> quando não
     * existe nenhum abastecimento para a bomba informada.
     */
    @Test
    void existsByBombaId_ShouldReturnFalseWhenNoAbastecimento() {
        // use a random non‑existent bomba id (e.g., 9999L)
        assertFalse(abastecimentoRepository.existsByBombaId(9999L));
    }
}