package br.com.gpfaltz.posto_combustivel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AbastecimentoResponse {

	private Long id;
	private Long bombaId;
	private String bombaNome;
	private LocalDate data;
	private BigDecimal precoPorLitro;
	private BigDecimal volume;
	private BigDecimal valorTotal;

	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBombaId() {
		return bombaId;
	}

	public void setBombaId(Long bombaId) {
		this.bombaId = bombaId;
	}

	public String getBombaNome() {
		return bombaNome;
	}

	public void setBombaNome(String bombaNome) {
		this.bombaNome = bombaNome;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getPrecoPorLitro() {
		return precoPorLitro;
	}

	public void setPrecoPorLitro(BigDecimal precoPorLitro) {
		this.precoPorLitro = precoPorLitro;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
}