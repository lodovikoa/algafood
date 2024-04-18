package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Problema")
public class Problem {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "2024-04-15T08:37:21.902245498Z")
    private OffsetDateTime timesTamp;

    @Schema(example = "http://algafood.com.br/dados-invalidos")
    private String type;

    @Schema(example = "Dados-invalidos")
    private String title;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
    private String detail;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
    private String userMessage;

    @Schema(description = "Lista de objetos ou campos que geraram o erro")
    private List<Object> objects;


    @Getter
    @Builder
    @Schema(name = "ObjetoProblema")
    public static class Object {

        @Schema(example = "Preço")
        private String name;

        @Schema(example = "O preço é inválido")
        private String userMessage;
    }

}
