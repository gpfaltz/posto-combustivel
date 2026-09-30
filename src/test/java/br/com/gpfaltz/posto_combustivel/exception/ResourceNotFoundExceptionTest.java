package br.com.gpfaltz.posto_combustivel.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void testInheritance() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        assertTrue(ex instanceof RuntimeException, "Should extend RuntimeException");
    }

    @Test
    void testMessagePropagation() {
        String msg = "Resource X not found";
        ResourceNotFoundException ex = new ResourceNotFoundException(msg);
        assertEquals(msg, ex.getMessage());
    }

    @Test
    void testSerialVersionUID() throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field field = ResourceNotFoundException.class.getDeclaredField("serialVersionUID");
        field.setAccessible(true);
        long uid = field.getLong(null);
        assertEquals(1L, uid);
    }
}