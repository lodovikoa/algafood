package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.input.GrupoInputDTO;
import com.algaworks.algafood.api.dto.model.GrupoModelDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Grupos")
public interface GrupoControllerOpenApi {
    @Operation(summary = "Listar os grupos")
    List<GrupoModelDTO> listar();


    @Operation(summary = "Buscar por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "ID da grupo inválido", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))}),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))})
    })
    GrupoModelDTO buscarPorId(Long idGrupo);

    @Operation(summary = "Cadastrar um grupo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grupo cadastrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GrupoModelDTO.class))}),
    })
    GrupoModelDTO cadastrar(GrupoInputDTO grupoInputDTO);

    @Operation(summary = "Atualizar um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo atualizado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GrupoModelDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))})
    })
    GrupoModelDTO alterar(GrupoInputDTO grupoInputDTO, Long grupoId);

    @Operation(summary = "Excluir um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Grupo excluído"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))})
    })
    void excluir(Long grupoId);
}
