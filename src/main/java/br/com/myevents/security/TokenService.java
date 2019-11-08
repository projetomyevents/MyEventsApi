package br.com.myevents.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Implementação dos serviços de JWT (Json Web Tokens).
 */
@Service
public class TokenService {

    /**
     * Chave usada pelo algoritmo de assinatura.
     */
    private final String secret =
            "cd+Pr1js+w2qfT2BoCD+tPcYp9LbjpmhSMEJqUob1mcxZ7+Wmik4AYdjX+DlDjmE4yporzQ9tm7v3z/j+QbdYg==";

    /**
     * Gera um token a partir de um email com duração de 7 dias.
     *
     * @param email o email
     * @return o token
     */
    public String generateAuthenticationToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(secret))
                .compact();
    }

    /**
     * Checa se o token é válido.
     *
     * @param token o token
     * @return {@code true} se o token é válido, {@code false} caso contrário
     */
    public boolean isValid(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            Instant expiration = claims.getExpiration().toInstant();
            return claims.getSubject() != null
                    && expiration != null
                    && Instant.now().isBefore(expiration);
        }
        return false;
    }

    /**
     * Retorna o email que foi usado para montar o token.
     *
     * @param token o token
     * @return o email
     */
    public String getEmail(String token) {
        Claims claims = getClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * Retorna {@link Claims} com as informações do token.
     *
     * @param token o token
     * @return as informações do token
     */
    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(TextCodec.BASE64.decode(secret)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

}
