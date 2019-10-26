package br.com.myevents.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Levantada para indicar que uma conta de usuário está desativada.
 */
public class UserAccountDisabledException extends AuthenticationException {

    public UserAccountDisabledException(String message) {
        super(message);
    }

}
