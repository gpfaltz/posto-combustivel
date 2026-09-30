package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para {@link BombaRequest} garantindo 100% de cobertura.
 */
class BombaRequestTest {

    private static Validator validator;

    @BeforeAll
    static void initValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private <T> Set<ConstraintViolation<T>> validate(T obj) {
        return validator.validate(obj);
    }

    @Test
    @DisplayName("Instancia válida não gera violações")
    void validInstance() {
        var request = new BombaRequest("Bomba 1", 5L);
        var violations = validate(request);
        assertTrue(violations.isEmpty(), "Nenhuma violação esperada para instância válida");
    }

    @Nested
    @DisplayName("Validações de campos obrigatórios")
    class NotBlankAndNotNull {
        @Test
        void nomeBlank() {
            var request = new BombaRequest("   ", 5L);
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Nome da bomba é obrigatório", v.getMessage());
        }

        @Test
        void nomeNull() {
            var request = new BombaRequest(null, 5L);
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Nome da bomba é obrigatório", v.getMessage());
        }

        @Test
        void combustivelIdNull() {
            var request = new BombaRequest("Bomba 1", null);
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("ID do combustível é obrigatório", v.getMessage());
        }
    }

    @Test
    @DisplayName("equals, hashCode e toString")
    void equalsHashCodeToString() {
        var r1 = new BombaRequest("Bomba X", 10L);
        var r2 = new BombaRequest("Bomba X", 10L);
        var r3 = new BombaRequest("Bomba Y", 10L);
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
        assertTrue(r1.toString().contains("nome=Bomba X"));
    }
}
