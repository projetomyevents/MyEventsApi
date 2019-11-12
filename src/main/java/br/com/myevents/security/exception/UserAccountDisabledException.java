package br.com.myevents.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Lançada se uma requisição de autenticação é rejeitada porque a conta de usuário está desativada.
 */
public class UserAccountDisabledException extends AuthenticationException {

    public UserAccountDisabledException(String message) {
        super(message);
    }

}
