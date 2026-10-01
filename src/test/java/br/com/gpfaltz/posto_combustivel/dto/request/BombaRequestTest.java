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
 * Testes unitários para {@link BombaRequest}.
 * Verifica validações de Bean Validation e comportamento de equals/hashCode.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=BombaRequestTest
 * </pre>
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

    /**
     * Garante que uma instância válida não gera violações.
     */
    @Test
    @DisplayName("Instancia válida não gera violações")
    void validInstance() {
        var request = new BombaRequest("Bomba 1", 5L);
        var violations = validate(request);
        assertTrue(violations.isEmpty(), "Nenhuma violação esperada para instância válida");
    }

    /**
     * Testes de restrições {@code @NotBlank} e {@code @NotNull}.
     */
    @Nested
    @DisplayName("Validações de campos obrigatórios")
    class NotBlankAndNotNull {
        /**
         * Campo {@code nome} não pode ser vazio.
         */
        @Test
        void nomeBlank() {
            var request = new BombaRequest("   ", 5L);
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Nome da bomba é obrigatório", v.getMessage());
        }

        /**
         * Campo {@code nome} não pode ser {@code null}.
         */
        @Test
        void nomeNull() {
            var request = new BombaRequest(null, 5L);
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("Nome da bomba é obrigatório", v.getMessage());
        }

        /**
         * Campo {@code combustivelId} não pode ser {@code null}.
         */
        @Test
        void combustivelIdNull() {
            var request = new BombaRequest("Bomba 1", null);
            var violations = validate(request);
            assertEquals(1, violations.size());
            var v = violations.iterator().next();
            assertEquals("ID do combustível é obrigatório", v.getMessage());
        }
    }

    /**
     * Verifica equals, hashCode e toString.
     */
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