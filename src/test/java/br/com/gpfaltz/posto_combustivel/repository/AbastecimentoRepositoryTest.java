package br.com.gpfaltz.posto_combustivel.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.gpfaltz.posto_combustivel.entity.Abastecimento;
import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;

@DataJpaTest
class AbastecimentoRepositoryTest {

	@Autowired
	private AbastecimentoRepository repository;

	@Autowired
	private BombaRepository bombaRepository;

	@Autowired
	private CombustivelRepository combustivelRepository;

	@Test
	void saveAndFind_ShouldPersist() {
		// Persist Combustivel first
		Combustivel combustivel = Combustivel.builder().nome("Álcool").precoPorLitro(new BigDecimal("3.50")).build();
		Combustivel savedCombustivel = combustivelRepository.save(combustivel);
		// Persist Bomba linked to Combustivel
		Bomba bomba = Bomba.builder().nome("Bomba 1").combustivel(savedCombustivel).build();
		Bomba savedBomba = bombaRepository.save(bomba);
		// Create Abastecimento linked to Bomba
		Abastecimento abastecimento = Abastecimento.builder().bomba(savedBomba).data(LocalDate.now())
				.volume(new BigDecimal("10")).valorTotal(new BigDecimal("35.00")).build();
		Abastecimento saved = repository.save(abastecimento);
		assertNotNull(saved.getId());
		Optional<Abastecimento> found = repository.findById(saved.getId());
		assertTrue(found.isPresent());
		assertEquals(savedBomba.getId(), found.get().getBomba().getId());
	}
}