package br.com.gpfaltz.posto_combustivel.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;

@DataJpaTest
class BombaRepositoryExistsTest {

    @Autowired
    private BombaRepository bombaRepository;

    @Autowired
    private CombustivelRepository combustivelRepository;

    @Test
    void existsByCombustivelId_ShouldReturnTrueWhenBombaExists() {
        // arrange: persist a combustivel and a bomba linked to it
        Combustivel combustivel = Combustivel.builder()
                .nome("Álcool")
                .precoPorLitro(new BigDecimal("3.50"))
                .build();
        Combustivel savedCombustivel = combustivelRepository.save(combustivel);

        Bomba bomba = Bomba.builder()
                .nome("Bomba 1")
                .combustivel(savedCombustivel)
                .build();
        bombaRepository.save(bomba);

        // act & assert
        assertTrue(bombaRepository.existsByCombustivelId(savedCombustivel.getId()));
    }

    @Test
    void existsByCombustivelId_ShouldReturnFalseWhenNoBomba() {
        // use a non‑existent combustivel id
        assertFalse(bombaRepository.existsByCombustivelId(9999L));
    }
}