package br.com.gpfaltz.posto_combustivel.dto.response;

import java.math.BigDecimal;

/**
 * DTO de resposta para informações de bomba.
 */
public record BombaResponse(
    Long id,
    String nome,
    Long combustivelId,
    String combustivelNome,
    BigDecimal precoPorLitro
) {}