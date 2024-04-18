package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringDocConfig {

    /*
    * Referencia:
    * https://www.youtube.com/watch?v=4AdvGglL0wY
    * https://www.youtube.com/watch?v=rFG2-_Wj4x0
    * */

    private static final String badRequestResponse = "BadRequestResponse";
    private static final String notFoundResponse = "NotFoundResponse";
    private static final String notAcceptableResponse = "NotAcceptableResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";

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
                )
                .components(new Components().schemas(this.gerarSchemas())
                        .responses(this.gerarResponses())
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
                    .forEach(pathItem -> pathItem.readOperationsMap()
                            .forEach(((httpMethod, operation) -> {
                                ApiResponses responses = operation.getResponses();
                                switch (httpMethod) {
                                    case GET:
                                        // responses.addApiResponse("404", new ApiResponse().$ref(notFoundResponse));
                                        responses.addApiResponse("406", new ApiResponse().$ref(notAcceptableResponse));
                                        responses.addApiResponse("500", new ApiResponse().description("Erro interno no servidor"));
                                        break;

                                    case POST:
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;

                                    case PUT:
                                        // responses.addApiResponse("404", new ApiResponse().$ref(notFoundResponse));
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;

                                    case DELETE:
                                        // responses.addApiResponse("404", new ApiResponse().$ref(notFoundResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    default:
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                }
                            })));
        };
    }

    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(Problem.class);
        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(MediaType.APPLICATION_JSON_VALUE, new io.swagger.v3.oas.models.media.MediaType().schema(new Schema<Problem>().$ref("Problema")));

        apiResponseMap.put(badRequestResponse, new ApiResponse().description("Requisição inválida").content(content));
        apiResponseMap.put(notFoundResponse, new ApiResponse().description("Recurso não encontrado").content(content));
        apiResponseMap.put(notAcceptableResponse, new ApiResponse().description("Recurso não possui representação que poderia ser aceita pelo consumidor").content(content));
        apiResponseMap.put(internalServerErrorResponse, new ApiResponse().description("Erro interno no servidor").content(content));

        return apiResponseMap;
    }
}
