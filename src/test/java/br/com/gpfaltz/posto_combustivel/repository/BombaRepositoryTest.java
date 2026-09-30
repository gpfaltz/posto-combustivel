package br.com.gpfaltz.posto_combustivel.repository;

import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;
import br.com.gpfaltz.posto_combustivel.repository.BombaRepository;
import br.com.gpfaltz.posto_combustivel.repository.CombustivelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BombaRepositoryTest {

    @Autowired
    private BombaRepository repository;

    @Autowired
    private CombustivelRepository combustivelRepository;

    @Test
    void saveAndFind_ShouldPersistWithCombustivel() {
        Combustivel combustivel = Combustivel.builder()
                .nome("Álcool")
                .precoPorLitro(new BigDecimal("3.50"))
                .build();
        // Persist combustivel first to satisfy foreign‑key constraint
        Combustivel savedCombustivel = combustivelRepository.save(combustivel);
        Bomba bomba = Bomba.builder()
                .nome("Bomba 1")
                .combustivel(savedCombustivel)
                .build();
        Bomba saved = repository.save(bomba);
        assertNotNull(saved.getId());
        Optional<Bomba> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Bomba 1", found.get().getNome());
    }
}