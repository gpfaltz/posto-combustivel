package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class CombustivelRequest {
	
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Preço por litro é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private BigDecimal precoPorLitro;

    // getters and setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPrecoPorLitro() { return precoPorLitro; }
    public void setPrecoPorLitro(BigDecimal precoPorLitro) { this.precoPorLitro = precoPorLitro; }
}
