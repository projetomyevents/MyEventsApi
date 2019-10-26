package br.com.myevents.exception;

/**
 * Levantada para indicar que um email de uma conta de usuário
 * já está vinculado a outra conta de usuário na base de dados.
 */
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String message) {
        super(message);
    }

}
