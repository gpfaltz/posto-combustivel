package br.com.gpfaltz.posto_combustivel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Posto de Abastecimento API")
                .description("API REST para cadastro e consulta de combustíveis, bombas e abastecimentos")
                .version("1.0.0"));
    }
}
