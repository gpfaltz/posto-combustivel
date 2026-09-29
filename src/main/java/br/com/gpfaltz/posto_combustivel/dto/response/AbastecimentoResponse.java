package br.com.gpfaltz.posto_combustivel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de resposta para informações de abastecimento.
 */
public class AbastecimentoResponse {

    private Long id;
    private Long bombaId;
    private String bombaNome;
    private LocalDate data;
    private BigDecimal precoPorLitro;
    private BigDecimal volume;
    private BigDecimal valorTotal;

    // getters and setters
    /** @return identificador do registro */
    public Long getId() { return id; }
    /** @param id identificador a ser definido */
    public void setId(Long id) { this.id = id; }
    /** @return identificador da bomba */
    public Long getBombaId() { return bombaId; }
    /** @param bombaId identificador da bomba */
    public void setBombaId(Long bombaId) { this.bombaId = bombaId; }
    /** @return nome da bomba */
    public String getBombaNome() { return bombaNome; }
    /** @param bombaNome nome da bomba */
    public void setBombaNome(String bombaNome) { this.bombaNome = bombaNome; }
    /** @return data do abastecimento */
    public LocalDate getData() { return data; }
    /** @param data data a ser definida */
    public void setData(LocalDate data) { this.data = data; }
    /** @return preço por litro do combustível */
    public BigDecimal getPrecoPorLitro() { return precoPorLitro; }
    /** @param precoPorLitro preço a ser definido */
    public void setPrecoPorLitro(BigDecimal precoPorLitro) { this.precoPorLitro = precoPorLitro; }
    /** @return volume abastecido */
    public BigDecimal getVolume() { return volume; }
    /** @param volume volume a ser definido */
    public void setVolume(BigDecimal volume) { this.volume = volume; }
    /** @return valor total calculado */
    public BigDecimal getValorTotal() { return valorTotal; }
    /** @param valorTotal valor total a ser definido */
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
}