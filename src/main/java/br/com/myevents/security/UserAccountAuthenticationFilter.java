package br.com.myevents.security;

import br.com.myevents.error.RequestError;
import br.com.myevents.security.exception.UserAccountCredentialsNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Processa uma requisição de autenticação em <strong>/user/login</strong>.
 */
public class UserAccountAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserAccountAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        super(new AntPathRequestMatcher("/user/login", "POST"));
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Método de autenticação não suportado, utilize o método POST.");
        }

        try {
            UserAccountCredentials userAccountCredentials = new ObjectMapper()
                    .readValue(request.getInputStream(), UserAccountCredentials.class);

            return authenticationManager.authenticate(new UserAccountAuthenticationToken(
                    userAccountCredentials.getEmail(), userAccountCredentials.getPassword()));
        } catch (IOException e) {
            throw new UserAccountCredentialsNotFoundException(
                    "Corpo da requisição não forma as credenciais de uma conta de usuário.");
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException {
        response.sendRedirect(String.format("/user/sucessful-authentication/%s",
                tokenService.generateAuthenticationToken(authResult.getPrincipal().toString())));
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException {
        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter()
                .append(new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .writeValueAsString(RequestError.builder()
                                .status(HttpStatus.UNAUTHORIZED)
                                .message(failed.getLocalizedMessage())
                                .build()));
    }

}
