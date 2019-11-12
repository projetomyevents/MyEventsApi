package br.com.myevents.exception;

/**
 * Lançada se um token está expirado.
 */
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) {
        super(message);
    }

}
