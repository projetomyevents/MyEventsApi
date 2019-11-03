package br.com.myevents.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro responsável por processar qualquer requisição de autenticação com o cabeçalho
 * <strong>Authorization</strong>.
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
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            UserAccountAuthenticationToken authenticationToken = getAuthentication(header.substring(7));
            if (authenticationToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * Retorna um {@link UserAccountAuthenticationToken} com as informações básicas de autenticação
     * de uma conta de usuário a partir de um token.
     *
     * @param token o token
     * @return o token com as informações básicas de autenticação de uma conta de usuário
     */
    private UserAccountAuthenticationToken getAuthentication(String token) {
        if (tokenService.isValid(token)) {
            UserAccountDetails userAccountDetails =
                    userAccountDetailsService.loadUserAccoutByEmail(tokenService.getEmail(token));
            return new UserAccountAuthenticationToken(userAccountDetails, null, userAccountDetails.getAuthorities());
        }
        return null;
    }

}
