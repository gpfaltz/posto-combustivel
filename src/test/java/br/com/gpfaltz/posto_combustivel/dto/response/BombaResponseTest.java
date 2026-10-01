package br.com.gpfaltz.posto_combustivel.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para {@link BombaResponse}.
 * Verifica getters, equals/hashCode, toString e serialização JSON.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=BombaResponseTest
 * </pre>
 */
class BombaResponseTest {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Verifica que os getters retornam os valores corretos.
     */
    @Test
    void gettersReturnProvidedValues() {
        BombaResponse resp = new BombaResponse(1L, "Bomba A", 2L, "Gasolina", new BigDecimal("4.20"));
        assertEquals(1L, resp.id());
        assertEquals("Bomba A", resp.nome());
        assertEquals(2L, resp.combustivelId());
        assertEquals("Gasolina", resp.combustivelNome());
        assertEquals(new BigDecimal("4.20"), resp.precoPorLitro());
    }

    /**
     * Verifica equals e hashCode.
     */
    @Test
    void equalsAndHashCodeWork() {
        BombaResponse b1 = new BombaResponse(1L, "Bomba", 2L, "Diesel", BigDecimal.ONE);
        BombaResponse b2 = new BombaResponse(1L, "Bomba", 2L, "Diesel", BigDecimal.ONE);
        BombaResponse b3 = new BombaResponse(2L, "Bomba", 2L, "Diesel", BigDecimal.ONE);
        assertEquals(b1, b2);
        assertEquals(b1.hashCode(), b2.hashCode());
        assertNotEquals(b1, b3);
    }

    /**
     * Verifica que o toString contém todos os campos.
     */
    @Test
    void toStringContainsAllFields() {
        BombaResponse resp = new BombaResponse(1L, "Bomba", 2L, "Etanol", new BigDecimal("5.55"));
        String s = resp.toString();
        assertTrue(s.contains("1"));
        assertTrue(s.contains("Bomba"));
        assertTrue(s.contains("2"));
        assertTrue(s.contains("Etanol"));
        assertTrue(s.contains("5.55"));
    }

    /**
     * Verifica a serialização/deserialização JSON.
     */
    @Test
    void jsonSerializationRoundTrip() throws Exception {
        BombaResponse original = new BombaResponse(1L, "Bomba", 2L, "Gasolina", new BigDecimal("4.20"));
        String json = mapper.writeValueAsString(original);
        BombaResponse deserialized = mapper.readValue(json, BombaResponse.class);
        assertEquals(original, deserialized);
    }

    /**
     * Verifica o comportamento com valores nulos.
     */
    @Test
    void handlesNullValues() {
        BombaResponse resp = new BombaResponse(null, null, null, null, null);
        assertNull(resp.id());
        assertNull(resp.nome());
        assertNull(resp.combustivelId());
        assertNull(resp.combustivelNome());
        assertNull(resp.precoPorLitro());
    }
}