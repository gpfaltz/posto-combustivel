package br.com.gpfaltz.posto_combustivel.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CombustivelResponseTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void gettersReturnProvidedValues() {
        CombustivelResponse resp = new CombustivelResponse(1L, "Gasolina", new BigDecimal("4.20"));
        assertEquals(1L, resp.id());
        assertEquals("Gasolina", resp.nome());
        assertEquals(new BigDecimal("4.20"), resp.precoPorLitro());
    }

    @Test
    void equalsAndHashCodeWork() {
        CombustivelResponse c1 = new CombustivelResponse(1L, "Diesel", BigDecimal.ONE);
        CombustivelResponse c2 = new CombustivelResponse(1L, "Diesel", BigDecimal.ONE);
        CombustivelResponse c3 = new CombustivelResponse(2L, "Diesel", BigDecimal.ONE);
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
    }

    @Test
    void toStringContainsAllFields() {
        CombustivelResponse resp = new CombustivelResponse(1L, "Etanol", new BigDecimal("5.55"));
        String s = resp.toString();
        assertTrue(s.contains("1"));
        assertTrue(s.contains("Etanol"));
        assertTrue(s.contains("5.55"));
    }

    @Test
    void jsonSerializationRoundTrip() throws Exception {
        CombustivelResponse original = new CombustivelResponse(1L, "Gasolina", new BigDecimal("4.20"));
        String json = mapper.writeValueAsString(original);
        CombustivelResponse deserialized = mapper.readValue(json, CombustivelResponse.class);
        assertEquals(original, deserialized);
    }

    @Test
    void handlesNullValues() {
        CombustivelResponse resp = new CombustivelResponse(null, null, null);
        assertNull(resp.id());
        assertNull(resp.nome());
        assertNull(resp.precoPorLitro());
    }
}
