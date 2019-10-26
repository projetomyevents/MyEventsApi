package br.com.myevents.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Levantada para indicar que uma conta de usuário está expirada.
 */
public class UserAccountExpiredException extends AuthenticationException {

    public UserAccountExpiredException(String message) {
        super(message);
    }

}
