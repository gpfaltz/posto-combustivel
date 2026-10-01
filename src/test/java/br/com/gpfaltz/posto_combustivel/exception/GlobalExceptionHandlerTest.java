package br.com.gpfaltz.posto_combustivel.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Testes unitários para {@link GlobalExceptionHandler}.
 * Verifica o tratamento de exceções customizadas e de validação de parâmetros.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=GlobalExceptionHandlerTest
 * </pre>
 */
class GlobalExceptionHandlerTest {

	private GlobalExceptionHandler handler;

	@BeforeEach
	void setUp() {
		handler = new GlobalExceptionHandler();
	}

	/**
	 * Verifica o tratamento da exceção {@link ResourceNotFoundException}.
	 */
	@Test
	void testHandleResourceNotFound() {
		String msg = "Item not found";
		ResourceNotFoundException ex = new ResourceNotFoundException(msg);
		ResponseEntity<String> response = handler.handleResourceNotFound(ex);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(msg, response.getBody());
	}

	/**
	 * Verifica o tratamento da exceção {@link IllegalStateException}.
	 */
	@Test
	void testHandleIllegalState() {
		String msg = "Conflict state";
		IllegalStateException ex = new IllegalStateException(msg);
		ResponseEntity<String> response = handler.handleIllegalState(ex);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals(msg, response.getBody());
	}

	/**
	 * Verifica o tratamento das exceções de validação de argumentos.
	 */
	@Test
	void testHandleValidationExceptions() {
		// Mock MethodArgumentNotValidException and its BindingResult
		MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(ex.getBindingResult()).thenReturn(bindingResult);

		FieldError fieldError1 = new FieldError("obj", "field1", "msg1");
		FieldError fieldError2 = new FieldError("obj", "field2", "msg2");
		when(bindingResult.getFieldErrors()).thenReturn(java.util.List.of(fieldError1, fieldError2));

		ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Map<String, String> errors = response.getBody();
		assertNotNull(errors);
		assertEquals(2, errors.size());
		assertEquals("msg1", errors.get("field1"));
		assertEquals("msg2", errors.get("field2"));
	}
}