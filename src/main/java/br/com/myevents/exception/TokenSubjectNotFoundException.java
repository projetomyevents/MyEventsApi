package br.com.myevents.exception;

/**
 * Lançada se um token não está vinculado a nada.
 */
public class TokenSubjectNotFoundException extends RuntimeException {

    public TokenSubjectNotFoundException(String message) {
        super(message);
    }

}
