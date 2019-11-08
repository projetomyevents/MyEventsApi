package br.com.myevents.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Representa as informações básicas de uma conta de usuário.
 */
@Getter
public class UserAccountAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    /**
     * O email da conta do usuário.
     */
    private final Object principal;

    /**
     * As credenciais da conta do usuário.
     */
    private Object credentials;

    public UserAccountAuthenticationToken(Object principal, Object credentials) {
        super(new ArrayList<>());
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(true);
    }

    public UserAccountAuthenticationToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(true);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

}
