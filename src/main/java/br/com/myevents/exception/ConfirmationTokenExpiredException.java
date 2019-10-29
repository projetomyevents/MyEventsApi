package br.com.myevents.exception;

/**
 * Levantada se um token de confirmação passou da sua data de validade.
 */
public class ConfirmationTokenExpiredException extends RuntimeException {

    public ConfirmationTokenExpiredException(String message) {
        super(message);
    }

}
