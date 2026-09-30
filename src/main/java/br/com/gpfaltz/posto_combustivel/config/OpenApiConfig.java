package br.com.gpfaltz.posto_combustivel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do OpenAPI/Swagger para a API do posto de abastecimento.
 * Define título, descrição e versão da documentação gerada.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Cria o bean {@link OpenAPI} customizado com informações da API.
     *
     * @return instância configurada de {@link OpenAPI}
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Posto de Combustível API")
                .description("API REST para cadastro e consulta de combustíveis, bombas e abastecimentos")
                .version("1.0.0"));
    }
}