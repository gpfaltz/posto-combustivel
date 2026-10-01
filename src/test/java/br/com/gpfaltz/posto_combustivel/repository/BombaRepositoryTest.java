package br.com.gpfaltz.posto_combustivel.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;

/**
 * Testes de integração para {@link BombaRepository}.
 * Verifica persistência e busca de bombas associadas a um combustível.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=BombaRepositoryTest
 * </pre>
 */
@DataJpaTest
class BombaRepositoryTest {

	@Autowired
	private BombaRepository repository;

	@Autowired
	private CombustivelRepository combustivelRepository;

	/**
	 * Verifica que a bomba é persistida e pode ser recuperada com o combustível associado.
	 */
	@Test
	void saveAndFind_ShouldPersistWithCombustivel() {
		Combustivel combustivel = Combustivel.builder().nome("Álcool").precoPorLitro(new BigDecimal("3.50")).build();
		// Persist combustivel first to satisfy foreign‑key constraint
		Combustivel savedCombustivel = combustivelRepository.save(combustivel);
		Bomba bomba = Bomba.builder().nome("Bomba 1").combustivel(savedCombustivel).build();
		Bomba saved = repository.save(bomba);
		assertNotNull(saved.getId());
		Optional<Bomba> found = repository.findById(saved.getId());
		assertTrue(found.isPresent());
		assertEquals("Bomba 1", found.get().getNome());
	}
}