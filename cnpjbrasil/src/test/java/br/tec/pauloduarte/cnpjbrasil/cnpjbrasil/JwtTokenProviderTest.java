package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.security;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setup() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecretString", "segredo-seguro-de-teste-seguro-seguro-seguro-1234567890");
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", 3600000);
    }

    @Test
    void generateAndValidateToken() {
        String token = jwtTokenProvider.generateToken("usuario@email.com", List.of("ROLE_USER"));
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals("usuario@email.com", jwtTokenProvider.getUsernameFromJWT(token));
        assertEquals(List.of("ROLE_USER"), jwtTokenProvider.getRolesFromJWT(token));
    }

    @Test
    void validateToken_ReturnsFalse_OnInvalidToken() {
        assertFalse(jwtTokenProvider.validateToken("token-invalido"));
    }
}
