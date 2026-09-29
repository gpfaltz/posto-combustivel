package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de requisição para criação/atualização de bomba.
 */
public class BombaRequest {

    @NotBlank(message = "Nome da bomba é obrigatório")
    private String nome;

    @NotNull(message = "ID do combustível é obrigatório")
    private Long combustivelId;

    // getters and setters
    /** @return nome da bomba */
    public String getNome() { return nome; }
    /** @param nome nome a ser definido */
    public void setNome(String nome) { this.nome = nome; }
    /** @return ID do combustível associado */
    public Long getCombustivelId() { return combustivelId; }
    /** @param combustivelId ID a ser definido */
    public void setCombustivelId(Long combustivelId) { this.combustivelId = combustivelId; }
}