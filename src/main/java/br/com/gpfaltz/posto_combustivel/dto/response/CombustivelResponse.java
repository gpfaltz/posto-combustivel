package br.com.gpfaltz.posto_combustivel.dto.response;

import java.math.BigDecimal;

public class CombustivelResponse {

	private Long id;
	private String nome;
	private BigDecimal precoPorLitro;

	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPrecoPorLitro() {
		return precoPorLitro;
	}

	public void setPrecoPorLitro(BigDecimal precoPorLitro) {
		this.precoPorLitro = precoPorLitro;
	}
}
