package br.com.shelfwise.exception;

/**
 * Exceção customizada para erros de regra de negócio.
 * Isso evita que a camada de serviço imprima erros na tela (System.out).
 */
public class ValidacaoException extends RuntimeException {
    
    public ValidacaoException(String message) {
        super(message);
    }
    
}