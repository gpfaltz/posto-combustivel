package br.com.gpfaltz.posto_combustivel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de integração que verifica se o contexto do Spring Boot é carregado corretamente.
 *
 * <p>Este teste garante que o bean principal da aplicação ({@link PostoCombustivelApplication})
 * está presente no {@link org.springframework.context.ApplicationContext}.</p>
 *
 * <p>Exemplo de execução via Maven:</p>
 * <pre>
 * mvn test -Dtest=PostoCombustivelApplicationTest
 * </pre>
 */
@SpringBootTest(classes = PostoCombustivelApplication.class)
class PostoCombustivelApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Verifica se o {@link ApplicationContext} foi inicializado e contém o bean da aplicação.
     *
     * <p>Não recebe parâmetros e não retorna valor. Caso o contexto não seja carregado ou o bean
     * não esteja presente, o teste falhará lançando {@link org.opentest4j.AssertionFailedError}.
     * </p>
     *
     * <p>Exemplo de uso: este método é invocado automaticamente pelo framework JUnit 5 quando
     * a classe de teste é executada.</p>
     */
    @Test
    void contextLoads() {
        // The context should be instantiated by Spring Boot
        assertThat(applicationContext).isNotNull();
        // Verify that the main application bean is present (bean name derived from class name)
        assertThat(applicationContext.containsBean("postoCombustivelApplication"))
                .as("Application bean should be present in the context")
                .isTrue();
    }
}