package br.com.gpfaltz.posto_combustivel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de resposta para informações de abastecimento.
 */
public record AbastecimentoResponse(
    Long id,
    Long bombaId,
    String bombaNome,
    LocalDate data,
    BigDecimal precoPorLitro,
    BigDecimal volume,
    BigDecimal valorTotal
) {}