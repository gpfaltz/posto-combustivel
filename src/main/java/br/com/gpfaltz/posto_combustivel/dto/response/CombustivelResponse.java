package br.com.gpfaltz.posto_combustivel.dto.response;

import java.math.BigDecimal;

/**
 * DTO de resposta para informações de combustível.
 */
public class CombustivelResponse {

    private Long id;
    private String nome;
    private BigDecimal precoPorLitro;

    // getters and setters
    /** @return identificador do combustível */
    public Long getId() { return id; }
    /** @param id identificador a ser definido */
    public void setId(Long id) { this.id = id; }
    /** @return nome do combustível */
    public String getNome() { return nome; }
    /** @param nome nome a ser definido */
    public void setNome(String nome) { this.nome = nome; }
    /** @return preço por litro */
    public BigDecimal getPrecoPorLitro() { return precoPorLitro; }
    /** @param precoPorLitro preço a ser definido */
    public void setPrecoPorLitro(BigDecimal precoPorLitro) { this.precoPorLitro = precoPorLitro; }
}