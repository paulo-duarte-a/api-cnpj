package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    private final Environment environment;

    public SwaggerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        OpenAPI openApi = new OpenAPI()
                .info(new Info()
                        .title("API CNPJ Brasil")
                        .version("1.0.0")
                        .description("API para consulta de dados de CNPJ")
                        .contact(new Contact()
                                .name("Paulo Duarte")
                                .email("contato@pauloduarte.tec.br")
                                .url("https://pauloduarte.tec.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );

        // Configuração do servidor apenas para produção
        if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
            openApi.addServersItem(new Server()
                    .url("https://apicnpj.pauloduarte.tec.br")
                    .description("Servidor de Produção"));
        }

        return openApi;
    }
}