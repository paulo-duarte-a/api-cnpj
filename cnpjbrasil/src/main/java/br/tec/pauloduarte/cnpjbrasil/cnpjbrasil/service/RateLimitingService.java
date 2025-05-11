package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.UsuarioRole;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    // Cache para os buckets por identificador de usuário (ex: email ou ID)
    private final Map<String, Bucket> userBuckets = new ConcurrentHashMap<>();

    // Configurações de FREE
    @Value("${rate-limit.free.capacity}") private long freeCapacity;
    @Value("${rate-limit.free.refill-tokens}") private long freeRefillTokens;
    @Value("${rate-limit.free.refill-period-minutes}") private long freeRefillMinutes;

    // Configurações de BASIC
    @Value("${rate-limit.basic.capacity}") private long basicCapacity;
    @Value("${rate-limit.basic.refill-tokens}") private long basicRefillTokens;
    @Value("${rate-limit.basic.refill-period-minutes}") private long basicRefillMinutes;

    // Configurações de PREMIUM
    @Value("${rate-limit.premium.capacity}") private long premiumCapacity;
    @Value("${rate-limit.premium.refill-tokens}") private long premiumRefillTokens;
    @Value("${rate-limit.premium.refill-period-minutes}") private long premiumRefillMinutes;

    // Configurações de ADMIN
    @Value("${rate-limit.admin.capacity}") private long adminCapacity;
    @Value("${rate-limit.admin.refill-tokens}") private long adminRefillTokens;
    @Value("${rate-limit.admin.refill-period-minutes}") private long adminRefillMinutes;


    public Bucket resolveBucketForUser(String userId, UsuarioRole role) {
        // A chave do bucket é o ID do usuário para garantir rate limiting por usuário.
        return userBuckets.computeIfAbsent(userId, id -> createNewBucket(role));
    }

    private Bucket createNewBucket(UsuarioRole role) {
        long capacity;
        long refillTokens;
        Duration refillDuration;

        switch (role) {
            case FREE:
                capacity = freeCapacity;
                refillTokens = freeRefillTokens;
                refillDuration = Duration.ofMinutes(freeRefillMinutes);
                break;
            case BASIC:
                capacity = basicCapacity;
                refillTokens = basicRefillTokens;
                refillDuration = Duration.ofMinutes(basicRefillMinutes);
                break;
            case PREMIUM:
                capacity = premiumCapacity;
                refillTokens = premiumRefillTokens;
                refillDuration = Duration.ofMinutes(premiumRefillMinutes);
                break;
            case ADMIN:
                capacity = adminCapacity;
                refillTokens = adminRefillTokens;
                refillDuration = Duration.ofMinutes(adminRefillMinutes);
                break;
            default: // Fallback para o mais restritivo ou lançar exceção
                throw new IllegalArgumentException("Role de usuário desconhecida para rate limiting: " + role);
        }

        Bandwidth limit = Bandwidth.builder()
                           .capacity(capacity)
                           .refillGreedy(refillTokens, refillDuration)
                           .build();

        return Bucket.builder().addLimit(limit).build();
    }
}