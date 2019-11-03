package br.com.myevents.security;

import br.com.myevents.exception.UserAccountDisabledException;
import br.com.myevents.exception.UserAccountExpiredException;
import br.com.myevents.exception.UserAccountLockedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Determina se uma requisição de autenticação é válida.
 */
public class UserAccountAuthenticationProvider implements AuthenticationProvider {

    private final UserAccountDetailsService userAccountDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UserAccountAuthenticationProvider(
            UserAccountDetailsService userAccountDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        this.userAccountDetailsService = userAccountDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserAccountDetails userAccountDetails = userAccountDetailsService.loadUserAccoutByEmail(email);
        if (!passwordEncoder.matches(password, userAccountDetails.getPassword())) {
            throw new BadCredentialsException("A senha da conta de usuário está incorreta.");
        }

        // checar por anomalias na conta do usuário
        if (!userAccountDetails.isEnabled()) {
            throw new UserAccountDisabledException("A conta de usuário está desativada.");
        }

        if (!userAccountDetails.isAccountNonLocked()) {
            throw new UserAccountLockedException("A conta de usuário está bloqueada.");
        }

        if (!userAccountDetails.isAccountNonExpired()) {
            throw new UserAccountExpiredException("A conta de usuário está expirada.");
        }

        return new UserAccountAuthenticationToken(email, password, userAccountDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserAccountAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
