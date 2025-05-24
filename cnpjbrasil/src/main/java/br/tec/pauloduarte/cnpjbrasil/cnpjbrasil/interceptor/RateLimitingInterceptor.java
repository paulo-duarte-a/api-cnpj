package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.interceptor;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Usuario;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.UsuarioRole;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.UsuarioRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.RateLimitingService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.lang.NonNull;


@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingInterceptor.class);

    @Autowired
    private RateLimitingService rateLimitingService;

    @Autowired
    private UsuarioRepository usuarioRepository; // Para buscar a role do usuário atual

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Se não houver autenticação, ou for anônimo, não aplicar rate limit (ou aplicar um rate limit de IP)
        // Para este exemplo, focamos em usuários autenticados.
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            return true; // Prossegue sem rate limiting para não autenticados ou tipos inesperados de principal
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername(); // O email é o username no nosso CustomUserDetailsService

        // Buscar o usuário do banco para obter a ROLE atualizada, pois a role no token JWT pode estar desatualizada
        // se for alterada após a emissão do token.
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElse(null); // Tratar caso não encontre (improvável se autenticado)

        if (usuario == null) {
            logger.warn("Usuário autenticado {} não encontrado no banco para rate limiting.", userEmail);
            // Decide como tratar: bloquear ou permitir. Bloquear é mais seguro.
            response.sendError(HttpStatus.FORBIDDEN.value(), "Usuário não encontrado para verificação de perfil.");
            return false;
        }

        UsuarioRole userRole = usuario.getRole();

        // Usar o email (ou ID do usuário) como chave para o bucket
        Bucket bucket = rateLimitingService.resolveBucketForUser(userEmail, userRole);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1); // Tenta consumir 1 token

        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true; // Requisição permitida
        } else {
            long waitForRefillNanos = probe.getNanosToWaitForRefill();
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefillNanos / 1_000_000_000L));
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),
                    "Você excedeu o limite de requisições para o seu perfil. Tente novamente mais tarde.");
            logger.info("Rate limit excedido para o usuário {} com perfil {}", userEmail, userRole);
            return false; // Requisição bloqueada
        }
    }
}