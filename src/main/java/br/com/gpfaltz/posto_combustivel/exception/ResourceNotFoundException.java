package br.com.gpfaltz.posto_combustivel.exception;

/**
 * Classe de exceção genérica para recursos não encontrados.
 * Extende {@link RuntimeException} para ser lançada em serviços quando
 * um registro não existe no banco de dados.
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor que recebe a mensagem de erro.
     *
     * @param message descrição do recurso ausente
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}