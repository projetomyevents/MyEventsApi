package br.com.myevents.exception;

/**
 * Lançada se um CPF de um usuário já está vinculado a um usuário.
 */
public class CPFExistsException extends RuntimeException {

    public CPFExistsException(String message) {
        super(message);
    }

}
