package br.com.myevents.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Levantada se um email não está vinculado a nenhuma conta de usuário conhecido.
 */
public class EmailNotFoundException extends AuthenticationException {

    public EmailNotFoundException(String msg) {
        super(msg);
    }

}
