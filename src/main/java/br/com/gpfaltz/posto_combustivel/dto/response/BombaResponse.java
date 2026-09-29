package br.com.gpfaltz.posto_combustivel.dto.response;

import java.math.BigDecimal;

/**
 * DTO de resposta para informações de bomba.
 */
public class BombaResponse {

    private Long id;
    private String nome;
    private Long combustivelId;
    private String combustivelNome;
    private BigDecimal precoPorLitro;

    // getters and setters
    /** @return identificador da bomba */
    public Long getId() { return id; }
    /** @param id identificador a ser definido */
    public void setId(Long id) { this.id = id; }
    /** @return nome da bomba */
    public String getNome() { return nome; }
    /** @param nome nome a ser definido */
    public void setNome(String nome) { this.nome = nome; }
    /** @return identificador do combustível associado */
    public Long getCombustivelId() { return combustivelId; }
    /** @param combustivelId id a ser definido */
    public void setCombustivelId(Long combustivelId) { this.combustivelId = combustivelId; }
    /** @return nome do combustível associado */
    public String getCombustivelNome() { return combustivelNome; }
    /** @param combustivelNome nome a ser definido */
    public void setCombustivelNome(String combustivelNome) { this.combustivelNome = combustivelNome; }
    /** @return preço por litro do combustível */
    public BigDecimal getPrecoPorLitro() { return precoPorLitro; }
    /** @param precoPorLitro preço a ser definido */
    public void setPrecoPorLitro(BigDecimal precoPorLitro) { this.precoPorLitro = precoPorLitro; }
}