package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.math.BigDecimal;

public class AbastecimentoRequest {
	
    @NotNull(message = "ID da bomba é obrigatório")
    private Long bombaId;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Volume é obrigatório")
    private BigDecimal volume;

    // getters and setters
    public Long getBombaId() { return bombaId; }
    public void setBombaId(Long bombaId) { this.bombaId = bombaId; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public BigDecimal getVolume() { return volume; }
    public void setVolume(BigDecimal volume) { this.volume = volume; }
}