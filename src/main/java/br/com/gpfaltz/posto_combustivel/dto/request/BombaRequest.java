package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BombaRequest {
	
    @NotBlank(message = "Nome da bomba é obrigatório")
    private String nome;

    @NotNull(message = "ID do combustível é obrigatório")
    private Long combustivelId;

    // getters and setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Long getCombustivelId() { return combustivelId; }
    public void setCombustivelId(Long combustivelId) { this.combustivelId = combustivelId; }
}
