package br.com.myevents.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Lançada se uma requisição de autenticação é rejeitada porque não existe um objeto
 * {@link br.com.myevents.security.UserAccountCredentials} em seu corpo (body).
 */
public class UserAccountCredentialsNotFoundException extends AuthenticationException {

    public UserAccountCredentialsNotFoundException(String msg) {
        super(msg);
    }

}
