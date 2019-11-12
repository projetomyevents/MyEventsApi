package br.com.myevents.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Processa o cabeçalho de autorização de uma requisição HTTP,
 * colocando o resultado em <code>SecurityContextHolder</code>.
 */
public class UserAccountAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserAccountDetailsService userAccountDetailsService;
    private final TokenService tokenService;

    public UserAccountAuthorizationFilter(
            AuthenticationManager authenticationManager,
            UserAccountDetailsService userAccountDetailsService,
            TokenService tokenService
    ) {
        super(authenticationManager);
        this.userAccountDetailsService = userAccountDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Optional.ofNullable(request.getHeader("Authorization")).ifPresent(authorization -> {
            if (authorization.startsWith("Bearer ")) {
                Optional.ofNullable(getAuthentication(authorization.substring(7))).ifPresent(
                        token -> SecurityContextHolder.getContext().setAuthentication(token));
            }
        });
        chain.doFilter(request, response);
    }

    /**
     * Cria e retorna um {@link UserAccountAuthenticationToken} com senha nula a partir do token de autenticação.
     *
     * @param token o token de autenticação
     * @return o {@link UserAccountAuthenticationToken} com senha nula
     */
    private UserAccountAuthenticationToken getAuthentication(String token) {
        return tokenService.isValid(token)
                ? new UserAccountAuthenticationToken(userAccountDetailsService.loadUserAccoutByEmail(
                        tokenService.getEmail(token)))
                : null;
    }

}
