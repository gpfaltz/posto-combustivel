package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * DTO de requisição para criação/atualização de combustível.
 */
public class CombustivelRequest {
	
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Preço por litro é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private BigDecimal precoPorLitro;

    // getters and setters
    /** @return nome do combustível */
    public String getNome() { return nome; }
    /** @param nome nome a ser definido */
    public void setNome(String nome) { this.nome = nome; }
    /** @return preço por litro */
    public BigDecimal getPrecoPorLitro() { return precoPorLitro; }
    /** @param precoPorLitro preço a ser definido */
    public void setPrecoPorLitro(BigDecimal precoPorLitro) { this.precoPorLitro = precoPorLitro; }
}