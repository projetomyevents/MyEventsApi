package br.com.myevents.security;

import br.com.myevents.error.RequestError;
import br.com.myevents.model.dto.UserAccountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public UserAccountAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        super(new AntPathRequestMatcher("/user/login", "POST"));
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST"))
            throw new AuthenticationServiceException(
                    String.format("Método '%s' de autenticação não suportado.", request.getMethod()));

        UserAccountDTO userAccount;
        try {
            userAccount = new ObjectMapper().readValue(request.getInputStream(), UserAccountDTO.class);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Argumentos inválidos.");
        }

        return authenticationManager.authenticate(
                new UserAccountAuthenticationToken(userAccount.getEmail(), userAccount.getPassword(), new ArrayList<>()));
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
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter()
                .append("{\"message\": \"Autenticação bem sucedida.\"}");
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException {
        RequestError requestError = RequestError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Falha na autenticação! Verifique se seu email e senha estão corretos.")
                .debugMessage(failed.getLocalizedMessage())
                .build();

        response.setStatus(requestError.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().append(new ObjectMapper().writeValueAsString(requestError));
    }

}
