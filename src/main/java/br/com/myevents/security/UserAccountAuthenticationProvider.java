package br.com.myevents.security;

import br.com.myevents.security.exception.UserAccountBadCredentialsException;
import br.com.myevents.security.exception.UserAccountDisabledException;
import br.com.myevents.security.exception.UserAccountExpiredException;
import br.com.myevents.security.exception.UserAccountLockedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Processa uma autenticação.
 */
@RequiredArgsConstructor
public class UserAccountAuthenticationProvider implements AuthenticationProvider {

    private final UserAccountDetailsService userAccountDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserAccountDetails userAccountDetails = userAccountDetailsService.loadUserAccoutByEmail(email);

        if (!passwordEncoder.matches(password, userAccountDetails.getPassword())) {
            throw new UserAccountBadCredentialsException("Senha incorreta");
        }

        if (!userAccountDetails.isEnabled()) {
            throw new UserAccountDisabledException("Conta de usuário desativada.");
        }

        if (!userAccountDetails.isAccountNonLocked()) {
            throw new UserAccountLockedException("Conta de usuário bloqueada.");
        }

        if (!userAccountDetails.isAccountNonExpired()) {
            throw new UserAccountExpiredException("Conta de usuário expirada.");
        }

        return new UserAccountAuthenticationToken(email, password, userAccountDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserAccountAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
