package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.interceptor;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Usuario;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.UsuarioRole;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.UsuarioRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.RateLimitingService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateLimitingInterceptorTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private RateLimitingService rateLimitingService;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @InjectMocks private RateLimitingInterceptor interceptor;

    @Test
    void preHandle_AllowsRequest_WhenUserFound() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("email@test.com");
        usuario.setRole(UsuarioRole.FREE);

        mockSecurityContext("email@test.com");
        when(usuarioRepository.findByEmail("email@test.com")).thenReturn(Optional.of(usuario));

        Bucket bucket = mock(Bucket.class);
        ConsumptionProbe probe = ConsumptionProbe.builder().consumed(1).remainingTokens(9).build();
        when(bucket.tryConsumeAndReturnRemaining(1)).thenReturn(probe);
        when(rateLimitingService.resolveBucketForUser(any(), any())).thenReturn(bucket);

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
    }

    private void mockSecurityContext(String email) {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(auth.isAuthenticated()).thenReturn(true);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);
    }
}
