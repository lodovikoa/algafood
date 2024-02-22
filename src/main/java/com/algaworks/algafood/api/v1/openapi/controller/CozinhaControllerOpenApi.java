package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.input.CozinhaInputDTO;
import com.algaworks.algafood.api.v1.dto.model.CozinhaModelDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @Operation(summary = "Listar cozinhas")
    PagedModel<CozinhaModelDTO> listar(@PageableDefault(size = 10) Pageable pageable);


    @Operation(summary = "Buscar uma cozinha pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))}),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))})
    })
    CozinhaModelDTO buscar(@PathVariable Long cozinhaId);

    @Operation(summary = "Cadastrar uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cozinha cadasgtrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CozinhaModelDTO.class))})
    })
    CozinhaModelDTO salvar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO);

    @Operation(summary = "Atualizar uma cozinha pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CozinhaModelDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))})
    })
    CozinhaModelDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO);

    @Operation(summary = "Excluir uma cozinha pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cozinha Excluida", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))})
    })
    void remover(@PathVariable Long cozinhaId);
}
