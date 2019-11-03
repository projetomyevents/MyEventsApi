package br.com.myevents.security;

import br.com.myevents.error.RequestError;
import br.com.myevents.model.dto.UserCredentialsDTO;
import br.com.myevents.utils.SimpleMessage;
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
import java.util.ArrayList;

/**
 * Filtro responsável por processar qualquer requisição de autenticação na url <strong>/user/login</strong>.
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

        UserCredentialsDTO userCredentials;
        try {
            userCredentials = objectMapper.readValue(request.getInputStream(), UserCredentialsDTO.class);
        } catch (Exception e) {
            throw new AuthenticationServiceException(
                    "Formato do corpo da requisição inválido para as credenciais de usuário.");
        }

        return authenticationManager.authenticate(new UserAccountAuthenticationToken(
                userCredentials.getEmail(), userCredentials.getPassword(), new ArrayList<>()));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException {
        response.addHeader("Authorization", String.format(
                "Bearer %s", tokenService.generateToken(authResult.getPrincipal().toString())));
        response.addHeader("access-control-expose-headers", "Authorization");
        response.setContentType("application/json");
        response.getWriter().append(objectMapper.writeValueAsString(SimpleMessage.builder()
                .message("Autenticação bem sucedida.")
                .build()));
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
