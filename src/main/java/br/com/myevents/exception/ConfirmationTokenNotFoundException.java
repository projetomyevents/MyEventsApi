package br.com.myevents.exception;

/**
 * Levantada se um token de confirmação não existe.
 */
public class ConfirmationTokenNotFoundException extends RuntimeException {

    public ConfirmationTokenNotFoundException(String message) {
        super(message);
    }

}
