package br.com.myevents.exception;

/**
 * Levantada se um email já está vinculado a um usuário.
 */
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String message) {
        super(message);
    }

}
