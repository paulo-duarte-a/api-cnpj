package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.config;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.interceptor.RateLimitingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RateLimitingInterceptor rateLimitingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Aplicar o interceptor a todas as rotas da API, exceto autenticação e Swagger
        registry.addInterceptor(rateLimitingInterceptor)
                .addPathPatterns("/api/**") // Aplica a todas as rotas sob /api
                .excludePathPatterns("/api/auth/**") // Exclui rotas de autenticação
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**"); // Exclui Swagger
    }
}