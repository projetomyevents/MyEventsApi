package br.com.myevents.exception;

/**
 * Lançada se um email já está vinculado a um usuário.
 */
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String message) {
        super(message);
    }

}
