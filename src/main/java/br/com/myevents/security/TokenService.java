package br.com.myevents.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

/**
 * Implementação dos serviços de JWT (Json Web Tokens).
 */
@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String SECRET;

    /**
     * Gera um token de autenticação a partir de um email com duração de 7 dias.
     *
     * @param email o email
     * @return o token de autenticação
     */
    public String generateAuthenticationToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(SECRET))
                .compact();
    }

    /**
     * Checa se o token de autenticação é válido.
     *
     * @param token o token de autenticação
     * @return {@code true} se o token de autenticação válido, {@code false} caso contrário
     */
    public boolean isValid(String token) {
        return Optional.ofNullable(getClaims(token))
                .map(claims -> {
                    Instant expiration = claims.getExpiration().toInstant();
                    return claims.getSubject() != null && expiration != null && Instant.now().isBefore(expiration);
                })
                .orElse(false);
    }

    /**
     * Retorna o email que foi usado para montar o token de autenticação.
     *
     * @param token o token de autenticação
     * @return o email
     */
    public String getEmail(String token) {
        return Optional.ofNullable(getClaims(token))
                .map(Claims::getSubject)
                .orElse(null);
    }

    /**
     * Retorna {@link Claims} com as informações do token de autenticação.
     *
     * @param token o token de autenticação
     * @return as informações do token de autenticação
     */
    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(TextCodec.BASE64.decode(SECRET)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

}
