package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.config.ratelimit;

import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração para o Bucket4j rate limiting.
 * Atualmente, os beans são autoconfigurados ou os limites são definidos via application.properties.
 * Esta classe pode ser usada para configurações mais avançadas no futuro,
 * como a configuração de rate limiting distribuído com JCache/Redis,
 * ou a definição de beans Bucket customizados, se necessário.
 */
@Configuration
public class RateLimitConfig {
    // Beans para configurações avançadas de rate limiting podem ser definidos aqui no futuro.
}