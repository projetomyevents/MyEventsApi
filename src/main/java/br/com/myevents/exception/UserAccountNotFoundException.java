package br.com.myevents.exception;

/**
 * Levantada se um usuário não existe.
 */
public class UserAccountNotFoundException extends RuntimeException {

    public UserAccountNotFoundException(String message) {
        super(message);
    }

}
