package br.com.myevents.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Levantada se um usuário, que está expirado, tentar autenticar-se.
 */
public class UserAccountExpiredException extends AuthenticationException {

    public UserAccountExpiredException(String message) {
        super(message);
    }

}
