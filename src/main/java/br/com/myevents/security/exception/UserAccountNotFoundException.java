package br.com.myevents.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Lançada se {@link br.com.myevents.security.UserAccountDetailsService} não pode localizar um
 * {@link br.com.myevents.model.User} através do seu email.
 */
public class UserAccountNotFoundException extends AuthenticationException {

    public UserAccountNotFoundException(String msg) {
        super(msg);
    }

}
