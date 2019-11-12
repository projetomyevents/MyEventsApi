package br.com.myevents.exception;

/**
 * Lançada se um usuário não existe.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
