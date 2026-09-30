package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * DTO de requisição para criação/atualização de abastecimento.
 */
public record AbastecimentoRequest(
    @NotNull(message = "ID da bomba é obrigatório") Long bombaId,
    @NotNull(message = "Data é obrigatória") LocalDate data,
    @NotNull(message = "Volume é obrigatório") BigDecimal volume
) {}