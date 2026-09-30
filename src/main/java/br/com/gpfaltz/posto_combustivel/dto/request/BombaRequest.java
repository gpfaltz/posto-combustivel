package br.com.gpfaltz.posto_combustivel.dto.request;

/**
 * DTO de requisição para criação/atualização de bomba.
 */
public record BombaRequest(
    @jakarta.validation.constraints.NotBlank(message = "Nome da bomba é obrigatório") String nome,
    @jakarta.validation.constraints.NotNull(message = "ID do combustível é obrigatório") Long combustivelId
) {}