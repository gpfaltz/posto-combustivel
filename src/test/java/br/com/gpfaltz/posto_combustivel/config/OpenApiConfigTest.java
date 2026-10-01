// Test class for OpenApiConfig ensuring 100% coverage
package br.com.gpfaltz.posto_combustivel.config;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * Testes unitários para a classe de configuração OpenAPI.
 * Verifica a criação do bean {@link OpenApiConfig#customOpenAPI()} e a presença das
 * anotações Spring necessárias.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=OpenApiConfigTest
 * </pre>
 */
public class OpenApiConfigTest {

    /**
     * Garante que o bean {@code OpenAPI} contém as informações corretas de título,
     * descrição e versão.
     */
    @Test
    @DisplayName("customOpenAPI should return OpenAPI with correct Info configuration")
    void testCustomOpenAPI_ReturnsConfiguredInfo() {
        OpenApiConfig config = new OpenApiConfig();
        OpenAPI openAPI = config.customOpenAPI();
        assertNotNull(openAPI, "OpenAPI bean must not be null");
        Info info = openAPI.getInfo();
        assertNotNull(info, "Info inside OpenAPI must not be null");
        assertEquals("Posto de Combustível API", info.getTitle(), "Title should match configuration");
        assertEquals("API REST para cadastro e consulta de combustíveis, bombas e abastecimentos", info.getDescription(), "Description should match configuration");
        assertEquals("1.0.0", info.getVersion(), "Version should match configuration");
    }

    /**
     * Verifica se a classe {@code OpenApiConfig} está anotada com {@link Configuration}.
     */
    @Test
    @DisplayName("OpenApiConfig class should be annotated with @Configuration")
    void testClassHasConfigurationAnnotation() {
        Configuration configuration = OpenApiConfig.class.getAnnotation(Configuration.class);
        assertNotNull(configuration, "OpenApiConfig must be annotated with @Configuration");
    }

    /**
     * Verifica se o método {@code customOpenAPI} está anotado com {@link Bean}.
     */
    @Test
    @DisplayName("customOpenAPI method should be annotated with @Bean")
    void testMethodHasBeanAnnotation() throws NoSuchMethodException {
        Method method = OpenApiConfig.class.getMethod("customOpenAPI");
        Bean bean = method.getAnnotation(Bean.class);
        assertNotNull(bean, "customOpenAPI method must be annotated with @Bean");
    }
}