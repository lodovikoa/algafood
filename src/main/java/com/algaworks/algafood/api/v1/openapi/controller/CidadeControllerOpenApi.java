package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.input.CidadeInputDTO;
import com.algaworks.algafood.api.v1.dto.model.CidadeModelDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Cidades")
public interface CidadeControllerOpenApi {

    @Operation(summary = "Listar as cidades", description = "Lista todas as Cidades")
    CollectionModel<CidadeModelDTO> listar();

    @Operation(summary = "Buscar uma cidade por ID", description = "Busca uma cidade a partir de um ID informado")
    CidadeModelDTO buscar(@Parameter(description = "ID da Cidade", example = "1",required = true) Long cidadeId);

    @Operation(summary = "Cadastrar uma cidade", description = "Cadastro de uma cidade precisa de um estado e um nome válido")
    CidadeModelDTO salvar(@RequestBody(description = "Representação de uma cidade", required = true) CidadeInputDTO cidadeInputDTO);

    @Operation(summary = "Alterar uma cidade", description = "Alteração de uma Cidade precisa de um Estado e um nome válido.")
    CidadeModelDTO alterar(@Parameter(description = "ID da Cidade", example = "1",required = true) Long cidadeId,
                           @RequestBody(description = "Representação de uma cidade", required = true) CidadeInputDTO cidadeInputDTO);

    @Operation(summary = "Excluir uma cidade", description = "Exclui uma Cidade a partir de um ID informado")
    void remover(@Parameter(description = "ID da Cidade", example = "1",required = true) Long cidadeId);
}
