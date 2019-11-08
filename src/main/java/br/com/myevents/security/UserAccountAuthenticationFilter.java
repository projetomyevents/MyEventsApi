package br.com.myevents.security;

import br.com.myevents.error.RequestError;
import br.com.myevents.model.dto.UserAccountCredentialsDTO;
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
 * Filtro responsável por processar qualquer requisição de autenticação no endpoint <strong>/user/login</strong>.
 */
public class UserAccountAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    private final ObjectMapper objectMapper;

    public UserAccountAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        super(new AntPathRequestMatcher("/user/login", "POST"));
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.objectMapper = new ObjectMapper();

        // especificar como uma data deve ser convertida pelo ObjectMapper
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    String.format("Método '%s' de autenticação não suportado.", request.getMethod()));
        }

        UserAccountCredentialsDTO userAccountCredentials;
        try {
            userAccountCredentials = objectMapper.readValue(request.getInputStream(), UserAccountCredentialsDTO.class);
        } catch (Exception e) {
            throw new AuthenticationServiceException(
                    "O conteúdo da requisição não forma as credenciais necessárias para uma conta de usuário.");
        }

        return authenticationManager.authenticate(new UserAccountAuthenticationToken(
                userAccountCredentials.getEmail(), userAccountCredentials.getPassword()));
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
        response.getWriter().append(objectMapper.writeValueAsString(
                RequestError.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message(failed.getLocalizedMessage())
                        .exception(failed.getClass().getSimpleName())
                        .build()));
    }

}
