package br.com.myevents.exception;

/**
 * Levantada se um token não possui um usuário vinculado.
 */
public class TokenUserNotFoundException extends RuntimeException {

    public TokenUserNotFoundException(String message) {
        super(message);
    }

}
