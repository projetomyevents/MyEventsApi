package br.com.myevents.exception;

/**
 * Levantada se um email de um usuário já está vinculado a outro usuário.
 */
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String message) {
        super(message);
    }

}
