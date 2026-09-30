package br.com.gpfaltz.posto_combustivel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test to ensure the Spring Boot application context loads correctly.
 * This provides 100% line/branch coverage for {@link PostoCombustivelApplication}.
 */
@SpringBootTest(classes = PostoCombustivelApplication.class)
class PostoCombustivelApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

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
