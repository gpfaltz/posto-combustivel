package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * DTO de requisição para criação/atualização de abastecimento.
 */
public class AbastecimentoRequest {

    @NotNull(message = "ID da bomba é obrigatório")
    private Long bombaId;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Volume é obrigatório")
    private BigDecimal volume;

    // getters and setters
    /** @return ID da bomba */
    public Long getBombaId() { return bombaId; }
    /** @param bombaId ID a ser definido */
    public void setBombaId(Long bombaId) { this.bombaId = bombaId; }
    /** @return data do abastecimento */
    public LocalDate getData() { return data; }
    /** @param data data a ser definida */
    public void setData(LocalDate data) { this.data = data; }
    /** @return volume abastecido */
    public BigDecimal getVolume() { return volume; }
    /** @param volume volume a ser definido */
    public void setVolume(BigDecimal volume) { this.volume = volume; }
}