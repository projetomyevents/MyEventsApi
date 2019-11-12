package br.com.myevents.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Lançada se uma requisição de autenticação é rejeitada porque a conta de usuário está bloqueada.
 */
public class UserAccountLockedException extends AuthenticationException {

    public UserAccountLockedException(String message) {
        super(message);
    }

}
