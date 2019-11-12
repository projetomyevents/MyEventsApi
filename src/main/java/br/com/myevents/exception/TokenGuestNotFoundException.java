package br.com.myevents.exception;

/**
 * Levantada se um token não possui um convidado vinculado.
 */
public class TokenGuestNotFoundException extends RuntimeException {

    public TokenGuestNotFoundException(String message) {
        super(message);
    }

}
