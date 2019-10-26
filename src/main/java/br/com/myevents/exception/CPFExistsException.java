package br.com.myevents.exception;

/**
 * Levantada para indicar que um CPF de um conta de usuário
 * já está vinculado a outra conta de usuário na base de dados.
 */
public class CPFExistsException extends RuntimeException {

    public CPFExistsException(String message) {
        super(message);
    }

}
