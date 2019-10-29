package br.com.myevents.exception;

/**
 * Levantada se um token de confirmação não possui um usuário vinculado.
 */
public class ConfirmationTokenUserNotFoundException extends RuntimeException {

    public ConfirmationTokenUserNotFoundException(String message) {
        super(message);
    }

}
