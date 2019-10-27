package br.com.myevents.security;

import br.com.myevents.model.dto.UserAccountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.time.Instant;
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
            throws AuthenticationException, IOException {
        if (!request.getMethod().equals("POST"))
            throw new AuthenticationServiceException(
                    String.format("Método '%s' de autenticação não suportado.", request.getMethod()));

        UserAccountDTO userAccount = new ObjectMapper().readValue(request.getInputStream(), UserAccountDTO.class);

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
                "Bearer%s",
                tokenService.generateToken((authResult.getPrincipal().toString()))));
        response.addHeader("access-control-expose-headers", "Authorization");
        response.setContentType("application/json");
        response.getWriter()
                .append("{\"message\": \"Autenticação bem sucedida.\"}");
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
                .append('{')
                .append("\"status\": 401,")
                .append(String.format("\"timestamp\": %s,", Instant.now().getEpochSecond()))
                .append("\"message\": \"Falha na autenticação! Verifique se seu email e senha estão corretos.\",")
                .append(String.format("\"debugMessage\": \"%s\",", failed.getLocalizedMessage()/*.replace('"', '\'')*/))
                .append('}');
    }

}
