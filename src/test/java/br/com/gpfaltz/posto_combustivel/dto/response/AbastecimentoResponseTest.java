package br.com.gpfaltz.posto_combustivel.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para {@link AbastecimentoResponse}.
 * Verifica getters, equals/hashCode, toString e serialização JSON.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=AbastecimentoResponseTest
 * </pre>
 */
class AbastecimentoResponseTest {
    // Register JavaTimeModule to handle LocalDate serialization
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    /**
     * Verifica que os getters retornam os valores fornecidos.
     */
    @Test
    void gettersReturnProvidedValues() {
        LocalDate date = LocalDate.of(2023, 5, 10);
        AbastecimentoResponse resp = new AbastecimentoResponse(
                1L, 2L, "Bomba 1", date,
                new BigDecimal("4.20"), new BigDecimal("10"), new BigDecimal("42.00"));
        assertEquals(1L, resp.id());
        assertEquals(2L, resp.bombaId());
        assertEquals("Bomba 1", resp.bombaNome());
        assertEquals(date, resp.data());
        assertEquals(new BigDecimal("4.20"), resp.precoPorLitro());
        assertEquals(new BigDecimal("10"), resp.volume());
        assertEquals(new BigDecimal("42.00"), resp.valorTotal());
    }

    /**
     * Verifica equals e hashCode.
     */
    @Test
    void equalsAndHashCodeWork() {
        AbastecimentoResponse a1 = new AbastecimentoResponse(1L, 2L, "Bomba", LocalDate.now(),
                BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        AbastecimentoResponse a2 = new AbastecimentoResponse(1L, 2L, "Bomba", a1.data(),
                BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        AbastecimentoResponse a3 = new AbastecimentoResponse(2L, 2L, "Bomba", a1.data(),
                BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1, a3);
    }

    /**
     * Verifica que o toString contém todos os campos.
     */
    @Test
    void toStringContainsAllFields() {
        AbastecimentoResponse resp = new AbastecimentoResponse(1L, 2L, "Bomba", LocalDate.of(2023,1,1),
                new BigDecimal("5"), new BigDecimal("10"), new BigDecimal("50"));
        String s = resp.toString();
        assertTrue(s.contains("1"));
        assertTrue(s.contains("2"));
        assertTrue(s.contains("Bomba"));
        assertTrue(s.contains("2023-01-01"));
        assertTrue(s.contains("5"));
        assertTrue(s.contains("10"));
        assertTrue(s.contains("50"));
    }

    /**
     * Verifica a serialização/deserialização JSON.
     */
    @Test
    void jsonSerializationRoundTrip() throws Exception {
        AbastecimentoResponse original = new AbastecimentoResponse(1L, 2L, "Bomba", LocalDate.of(2023,5,10),
                new BigDecimal("4.20"), new BigDecimal("10"), new BigDecimal("42.00"));
        String json = mapper.writeValueAsString(original);
        AbastecimentoResponse deserialized = mapper.readValue(json, AbastecimentoResponse.class);
        assertEquals(original, deserialized);
    }

    /**
     * Verifica o comportamento com valores nulos.
     */
    @Test
    void handlesNullValues() {
        AbastecimentoResponse resp = new AbastecimentoResponse(null, null, null, null, null, null, null);
        assertNull(resp.id());
        assertNull(resp.bombaId());
        assertNull(resp.bombaNome());
        assertNull(resp.data());
        assertNull(resp.precoPorLitro());
        assertNull(resp.volume());
        assertNull(resp.valorTotal());
    }
}