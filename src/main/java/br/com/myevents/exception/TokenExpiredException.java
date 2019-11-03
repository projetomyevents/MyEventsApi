package br.com.myevents.exception;

/**
 * Levantada se um token passou da sua data de validade.
 */
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) {
        super(message);
    }

}
