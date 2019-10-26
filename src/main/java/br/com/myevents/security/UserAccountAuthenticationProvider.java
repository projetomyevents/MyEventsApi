package br.com.myevents.security;

import br.com.myevents.exception.UserAccountDisabledException;
import br.com.myevents.exception.UserAccountExpiredException;
import br.com.myevents.exception.UserAccountLockedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Determina se uma requisição de autênticação é válida.
 */
public class UserAccountAuthenticationProvider implements AuthenticationProvider {

    private final Log logger = LogFactory.getLog(getClass());

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
            throw new BadCredentialsException(String.format("Autênticação falhou para '%s'.", email));
        }

        // checar por anomalias na conta de usuário
        if (!userAccountDetails.isAccountNonLocked()) {
            logger.debug("Conta de usuário está bloqueada");
            throw new UserAccountLockedException("Conta de usuário está bloqueada.");
        }

        if (!userAccountDetails.isEnabled()) {
            logger.debug("Conta de usuário está desativada");
            throw new UserAccountDisabledException("Conta de usuário está desativada.");
        }

        if (!userAccountDetails.isAccountNonExpired()) {
            logger.debug("Conta de usuário está expirada");
            throw new UserAccountExpiredException("Conta de usuário está expirada.");
        }

        return new UserAccountAuthenticationToken(email, password, userAccountDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserAccountAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
