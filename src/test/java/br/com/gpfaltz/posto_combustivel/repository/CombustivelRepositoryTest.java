package br.com.gpfaltz.posto_combustivel.repository;

import br.com.gpfaltz.posto_combustivel.entity.Combustivel;
import br.com.gpfaltz.posto_combustivel.repository.CombustivelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CombustivelRepositoryTest {

    @Autowired
    private CombustivelRepository repository;

    @Test
    void saveAndFindById_ShouldPersistEntity() {
        Combustivel combustivel = Combustivel.builder()
                .nome("Álcool")
                .precoPorLitro(new BigDecimal("3.50"))
                .build();
        Combustivel saved = repository.save(combustivel);
        assertNotNull(saved.getId());
        Optional<Combustivel> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Álcool", found.get().getNome());
    }
}
