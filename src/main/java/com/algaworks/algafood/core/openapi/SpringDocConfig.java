package com.algaworks.algafood.core.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SpringDocConfig {

    /*
    * Referencia:
    * https://www.youtube.com/watch?v=4AdvGglL0wY
    * https://www.youtube.com/watch?v=rFG2-_Wj4x0
    * */

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Algafood API")
                        .version("v1")
                        .description("Documentação - REST API Algafood")
                        .license(new License()
                                .name("Algafood-licenças")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Complementação")
                        .url("https://teste.com"))
                .tags(
                        Arrays.asList(
                                new Tag().name("Cidades").description("Gerenciar as cidades"),
                                new Tag().name("Grupos").description("Gerenciar grupos de usuários"),
                                new Tag().name("Cozinhas").description("Gerenciar as cozinhas")
                        )
                );

    }
}
