package com.algaworks.algafood.core.openapi;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
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

//    @Bean
//    public GroupedOpenApi groupedOpenApi_v1() {
//        return GroupedOpenApi.builder()
//                .group("Algafood API v1")
//                .pathsToMatch("/v1/**")
//                .addOpenApiCustomizer( openApi -> {
//                    openApi.info(new Info()
//                                    .title("Algafood API")
//                                    .version("v1")
//                                    .description("Documentação - REST API Algafood")
//                                    .license(new License()
//                                            .name("Algafood-licenças")
//                                            .url("https://springdoc.org")))
//                            .externalDocs(new ExternalDocumentation()
//                                    .description("Complementação")
//                                    .url("https://teste.com"))
//                            .tags(
//                                    Arrays.asList(
//                                            new Tag().name("Cidades").description("Gerenciar as cidades"),
//                                            new Tag().name("Grupos").description("Gerenciar grupos de usuários"),
//                                            new Tag().name("Cozinhas").description("Gerenciar as cozinhas")
//                                    )
//                            );
//                })
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi groupedOpenApi_v2() {
//        return GroupedOpenApi.builder()
//                .group("Algafood API v2")
//                .pathsToMatch("/v2/**")
//                .addOpenApiCustomizer( openApi -> {
//                    openApi.info(new Info()
//                                    .title("Algafood API")
//                                    .version("v1")
//                                    .description("Documentação - REST API Algafood")
//                                    .license(new License()
//                                            .name("Algafood-licenças")
//                                            .url("https://springdoc.org")))
//                            .externalDocs(new ExternalDocumentation()
//                                    .description("Complementação")
//                                    .url("https://teste.com"))
//                            .tags(
//                                    Arrays.asList(
//                                            new Tag().name("Cozinhas").description("Gerenciar as cozinhas")
//                                    )
//                            );
//                })
//                .build();
//    }

    @Bean
    public OpenApiCustomizer openApiCustomizer_v1() {
        return openApi -> {
            openApi
                    .getPaths()
                    .values()
                    .stream()
                    .flatMap(pathItem -> pathItem.readOperations().stream())
                    .forEach(operation -> {
                        ApiResponses responses = operation.getResponses();

                        ApiResponse apiResponseErroIntgerno = new ApiResponse().description("Erro interno no servidor");
                        ApiResponse apiResponseRecursoNaoEncontrado = new ApiResponse().description("Recurso não encontrado");
                        ApiResponse apiResponseSemRepresentacao = new ApiResponse().description("Recurso não possui uma representação que poderia ser aceita pelo consumidor");

                        responses.addApiResponse("404", apiResponseRecursoNaoEncontrado);
                        responses.addApiResponse("406", apiResponseSemRepresentacao);
                        responses.addApiResponse("500",apiResponseErroIntgerno);
                    });
        };
    }
}
