package br.com.myevents.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Levantada se um usuário, que está desativado, tentar autenticar-se.
 */
public class UserAccountDisabledException extends AuthenticationException {

    public UserAccountDisabledException(String message) {
        super(message);
    }

}
