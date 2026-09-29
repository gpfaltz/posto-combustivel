package br.com.gpfaltz.posto_combustivel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot para o módulo "Posto de Abastecimento".
 * Responsável por iniciar o contexto da aplicação.
 */
@SpringBootApplication
public class PostoAbastecimentoApplication {
    /**
     * Ponto de entrada da aplicação.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        SpringApplication.run(PostoAbastecimentoApplication.class, args);
    }
}