package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para {@link AbastecimentoRequest} garantindo 100% de cobertura.
 */
class AbastecimentoRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private <T> Set<ConstraintViolation<T>> validate(T obj) {
        return validator.validate(obj);
    }

    @Test
    @DisplayName("Instancia válida não gera violações")
    void validInstance() {
        var request = new AbastecimentoRequest(1L, LocalDate.now(), new BigDecimal("10.5"));
        Set<ConstraintViolation<AbastecimentoRequest>> violations = validate(request);
        assertTrue(violations.isEmpty(), "Não deve haver violações para instância válida");
    }

    @Nested
    @DisplayName("Validações de campos obrigatórios")
    class NotNullConstraints {
        @Test
        void bombaIdNull() {
            var request = new AbastecimentoRequest(null, LocalDate.now(), new BigDecimal("5"));
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("ID da bomba é obrigatório", v.getMessage());
        }

        @Test
        void dataNull() {
            var request = new AbastecimentoRequest(1L, null, new BigDecimal("5"));
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Data é obrigatória", v.getMessage());
        }

        @Test
        void volumeNull() {
            var request = new AbastecimentoRequest(1L, LocalDate.now(), null);
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Volume é obrigatório", v.getMessage());
        }
    }

    @Test
    @DisplayName("equals, hashCode e toString funcionam corretamente")
    void equalsHashCodeToString() {
        var r1 = new AbastecimentoRequest(2L, LocalDate.of(2023, 1, 1), new BigDecimal("20"));
        var r2 = new AbastecimentoRequest(2L, LocalDate.of(2023, 1, 1), new BigDecimal("20"));
        var r3 = new AbastecimentoRequest(3L, LocalDate.of(2023, 1, 1), new BigDecimal("20"));
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
        assertTrue(r1.toString().contains("bombaId=2"));
    }
}
