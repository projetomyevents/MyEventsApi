package br.com.myevents.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Levantada se um usuário, que está bloqueado, tentar autenticar-se.
 */
public class UserAccountLockedException extends AuthenticationException {

    public UserAccountLockedException(String message) {
        super(message);
    }

}
