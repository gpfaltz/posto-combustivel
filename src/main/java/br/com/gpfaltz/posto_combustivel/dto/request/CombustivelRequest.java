package br.com.gpfaltz.posto_combustivel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * DTO de requisição para criação/atualização de combustível.
 */
public record CombustivelRequest(
    @NotBlank(message = "Nome é obrigatório") String nome,
    @NotNull(message = "Preço por litro é obrigatório")
    @Positive(message = "Preço deve ser positivo") BigDecimal precoPorLitro
) {}