package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.input.CidadeInputDTO;
import com.algaworks.algafood.api.v1.dto.model.CidadeModelDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Cidades")
public interface CidadeControllerOpenApi {

    @Operation(summary = "Listar as cidades")
    CollectionModel<CidadeModelDTO> listar();

    @Operation(summary = "Buscar uma cidade por ID")
    CidadeModelDTO buscar(Long cidadeId);

    @Operation(summary = "Cadastrar uma cidade")
    CidadeModelDTO salvar(CidadeInputDTO cidadeInputDTO);

    @Operation(summary = "Alterar uma cidade")
    CidadeModelDTO alterar(Long cidadeId, CidadeInputDTO cidadeInputDTO);

    @Operation(summary = "Excluir uma cidade")
    void remover(Long cidadeId);
}
