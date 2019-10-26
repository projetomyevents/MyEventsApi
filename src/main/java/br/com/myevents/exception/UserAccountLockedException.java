package br.com.myevents.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Levantada para indicar que uma conta de usuário está bloqueada.
 */
public class UserAccountLockedException extends AuthenticationException {

    public UserAccountLockedException(String message) {
        super(message);
    }

}
