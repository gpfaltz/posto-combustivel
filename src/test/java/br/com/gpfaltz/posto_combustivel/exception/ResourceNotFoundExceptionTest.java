package br.com.gpfaltz.posto_combustivel.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para {@link ResourceNotFoundException}.
 * Verifica herança de {@link RuntimeException} e propagação da mensagem.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=ResourceNotFoundExceptionTest
 * </pre>
 */
class ResourceNotFoundExceptionTest {

    /**
     * Verifica que a exceção estende {@link RuntimeException}.
     */
    @Test
    void testInheritance() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        assertTrue(ex instanceof RuntimeException, "Should extend RuntimeException");
    }

    /**
     * Verifica que a mensagem passada ao construtor é preservada.
     */
    @Test
    void testMessagePropagation() {
        String msg = "Resource X not found";
        ResourceNotFoundException ex = new ResourceNotFoundException(msg);
        assertEquals(msg, ex.getMessage());
    }

    /**
     * Verifica o valor de {@code serialVersionUID}.
     */
    @Test
    void testSerialVersionUID() throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field field = ResourceNotFoundException.class.getDeclaredField("serialVersionUID");
        field.setAccessible(true);
        long uid = field.getLong(null);
        assertEquals(1L, uid);
    }
}