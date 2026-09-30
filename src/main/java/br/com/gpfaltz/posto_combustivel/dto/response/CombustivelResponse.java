package br.com.gpfaltz.posto_combustivel.dto.response;

import java.math.BigDecimal;

/**
 * DTO de resposta para informações de combustível.
 */
public record CombustivelResponse(
    Long id,
    String nome,
    BigDecimal precoPorLitro
) {}