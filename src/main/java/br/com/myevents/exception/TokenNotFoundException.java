package br.com.myevents.exception;

/**
 * Levantada se um token não existe.
 */
public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String message) {
        super(message);
    }

}
