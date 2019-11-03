package br.com.myevents.exception;

/**
 * Levantada se um CPF de um usuário já está vinculado a outro usuário.
 */
public class CPFExistsException extends RuntimeException {

    public CPFExistsException(String message) {
        super(message);
    }

}
