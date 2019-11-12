package br.com.myevents.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Lançada se uma requisição de autenticação é rejeitada porque suas credenciais são inválidas.
 */
public class UserAccountBadCredentialsException extends AuthenticationException {

    public UserAccountBadCredentialsException(String msg) {
        super(msg);
    }

}
