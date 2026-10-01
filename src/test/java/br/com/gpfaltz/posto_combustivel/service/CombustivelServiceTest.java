package br.com.gpfaltz.posto_combustivel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.gpfaltz.posto_combustivel.dto.request.CombustivelRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.CombustivelResponse;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;
import br.com.gpfaltz.posto_combustivel.exception.ResourceNotFoundException;
import br.com.gpfaltz.posto_combustivel.repository.BombaRepository;
import br.com.gpfaltz.posto_combustivel.repository.CombustivelRepository;

/**
 * Testes unitários para {@link CombustivelService}.
 * Verifica criação, atualização, busca, listagem e exclusão de combustíveis.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=CombustivelServiceTest
 * </pre>
 */
class CombustivelServiceTest {

	@Mock
	private CombustivelRepository repository;

	@Mock
	private BombaRepository bombaRepository;

	@InjectMocks
	private CombustivelService service;

	private Combustivel combustivel;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		combustivel = Combustivel.builder().id(1L).nome("Álcool").precoPorLitro(new BigDecimal("3.50")).build();
	}

	/**
	 * Verifica que a criação persiste e retorna a resposta.
	 */
	@Test
	void create_ShouldPersistAndReturnResponse() {
		CombustivelRequest request = new CombustivelRequest("Gasolina", new BigDecimal("4.20"));
		when(repository.save(any(Combustivel.class))).thenAnswer(i -> {
			Combustivel c = i.getArgument(0);
			c.setId(2L);
			return c;
		});
		CombustivelResponse resp = service.create(request);
		assertEquals(2L, resp.id());
		assertEquals("Gasolina", resp.nome());
		assertEquals(new BigDecimal("4.20"), resp.precoPorLitro());
	}

	/**
	 * Verifica que a listagem mapeia as respostas.
	 */
	@Test
	void getAll_ShouldMapResponses() {
		when(repository.findAll()).thenReturn(Collections.singletonList(combustivel));
		var list = service.getAll();
		assertEquals(1, list.size());
		CombustivelResponse resp = list.get(0);
		assertEquals(combustivel.getId(), resp.id());
		assertEquals(combustivel.getNome(), resp.nome());
	}

	/**
	 * Verifica que a exclusão lança exceção quando há bombas associadas.
	 */
	@Test
	void delete_WhenHasBomba_ShouldThrowIllegalState() {
		when(repository.existsById(1L)).thenReturn(true);
		when(bombaRepository.existsByCombustivelId(1L)).thenReturn(true);
		assertThrows(IllegalStateException.class, () -> service.delete(1L));
	}

	/**
	 * Verifica que a exclusão lança exceção quando o registro não existe.
	 */
	@Test
	void delete_WhenNotFound_ShouldThrowResourceNotFound() {
		when(repository.existsById(5L)).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> service.delete(5L));
	}

	/**
	 * Verifica que a busca por ID retorna a resposta quando encontrada.
	 */
	@Test
	void getById_WhenExists_ShouldReturnResponse() {
		when(repository.findById(1L)).thenReturn(Optional.of(combustivel));
		CombustivelResponse resp = service.getById(1L);
		assertEquals(combustivel.getId(), resp.id());
		assertEquals(combustivel.getNome(), resp.nome());
		assertEquals(combustivel.getPrecoPorLitro(), resp.precoPorLitro());
	}

	/**
	 * Verifica que a busca por ID lança exceção quando não encontrada.
	 */
	@Test
	void getById_WhenNotFound_ShouldThrowException() {
		when(repository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> service.getById(99L));
	}

	/**
	 * Verifica que a atualização persiste e retorna a resposta.
	 */
	@Test
	void update_ShouldReturnUpdatedResponse() {
		CombustivelRequest request = new CombustivelRequest("Diesel", new BigDecimal("5.00"));
		when(repository.findById(1L)).thenReturn(Optional.of(combustivel));
		when(repository.save(any(Combustivel.class))).thenAnswer(i -> {
			Combustivel c = i.getArgument(0);
			c.setId(1L);
			return c;
		});
		CombustivelResponse resp = service.update(1L, request);
		assertEquals(1L, resp.id());
		assertEquals("Diesel", resp.nome());
		assertEquals(new BigDecimal("5.00"), resp.precoPorLitro());
	}

	/**
	 * Verifica que a atualização lança exceção quando o registro não existe.
	 */
	@Test
	void update_WhenNotFound_ShouldThrowException() {
		CombustivelRequest request = new CombustivelRequest("Diesel", new BigDecimal("5.00"));
		when(repository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> service.update(99L, request));
	}

	/**
	 * Verifica que a exclusão funciona quando não há bombas.
	 */
	@Test
	void delete_WhenExistsAndNoBomba_ShouldDeleteSuccessfully() {
		when(repository.existsById(1L)).thenReturn(true);
		when(bombaRepository.existsByCombustivelId(1L)).thenReturn(false);
		service.delete(1L);
		verify(repository, times(1)).deleteById(1L);
	}
}
