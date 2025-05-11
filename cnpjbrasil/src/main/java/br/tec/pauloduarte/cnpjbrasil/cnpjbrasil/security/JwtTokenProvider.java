package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt.secret}")
    private String jwtSecretString;

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;

    private SecretKey getSigningKey() {
        // A chave precisa ter um tamanho mínimo dependendo do algoritmo (ex: HS512 requer 512 bits / 64 bytes)
        // Se a chave for menor, o jjwt-impl pode lançar um erro.
        // Esta é uma forma simples de garantir o tamanho, mas em produção considere uma chave gerada externamente.
        byte[] keyBytes = jwtSecretString.getBytes();
        if (keyBytes.length < 64 && jwtSecretString.length() < 64) { // Exemplo para HS512
             // Pad para garantir o tamanho. NÃO FAÇA ISSO EM PRODUÇÃO REAL COM UMA CHAVE FRACA.
             // Use uma chave forte e de tamanho adequado desde o início.
            String paddedKey = jwtSecretString;
            while (paddedKey.length() < 64) {
                paddedKey += "0"; // Apenas para exemplo, NÃO é seguro para chaves reais.
            }
            keyBytes = paddedKey.substring(0,64).getBytes();

        } else if (keyBytes.length > 64) {
             keyBytes = java.util.Arrays.copyOf(keyBytes, 64);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return generateToken(userPrincipal.getUsername());
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Assinatura JWT inválida");
        } catch (MalformedJwtException ex) {
            logger.error("Token JWT inválido");
        } catch (ExpiredJwtException ex) {
            logger.error("Token JWT expirado");
        } catch (UnsupportedJwtException ex) {
            logger.error("Token JWT não suportado");
        } catch (IllegalArgumentException ex) {
            logger.error("Claims JWT vazias");
        }
        return false;
    }
}