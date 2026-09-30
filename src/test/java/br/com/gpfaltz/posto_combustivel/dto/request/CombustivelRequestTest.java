package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para {@link CombustivelRequest} garantindo 100% de cobertura.
 */
class CombustivelRequestTest {

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
        var request = new CombustivelRequest("Gasolina", new BigDecimal("5.99"));
        var violations = validate(request);
        assertTrue(violations.isEmpty(), "Nenhuma violação esperada para instância válida");
    }

    @Nested
    @DisplayName("Validações de campos obrigatórios")
    class FieldConstraints {
        @Test
        void nomeBlank() {
            var request = new CombustivelRequest("   ", new BigDecimal("5"));
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Nome é obrigatório", v.getMessage());
        }

        @Test
        void nomeNull() {
            var request = new CombustivelRequest(null, new BigDecimal("5"));
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Nome é obrigatório", v.getMessage());
        }

        @Test
        void precoNull() {
            var request = new CombustivelRequest("Diesel", null);
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Preço por litro é obrigatório", v.getMessage());
        }

        @Test
        void precoZero() {
            var request = new CombustivelRequest("Etanol", BigDecimal.ZERO);
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Preço deve ser positivo", v.getMessage());
        }

        @Test
        void precoNegativo() {
            var request = new CombustivelRequest("Etanol", new BigDecimal("-1"));
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Preço deve ser positivo", v.getMessage());
        }
    }

    @Test
    @DisplayName("equals, hashCode e toString")
    void equalsHashCodeToString() {
        var r1 = new CombustivelRequest("Álcool", new BigDecimal("4.50"));
        var r2 = new CombustivelRequest("Álcool", new BigDecimal("4.50"));
        var r3 = new CombustivelRequest("Álcool", new BigDecimal("5.00"));
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
        assertTrue(r1.toString().contains("nome=Álcool"));
    }
}
