package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.model.RestauranteApenasNomeModelDTO;
import com.algaworks.algafood.api.v1.dto.model.RestauranteBasicoModelDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @Operation(parameters = {
            @Parameter(name = "projeção", description = "Nome da projeção", example = "apenas-nome", in = ParameterIn.QUERY, required = false)
    })
    public CollectionModel<RestauranteBasicoModelDTO> listar();

    public CollectionModel<RestauranteApenasNomeModelDTO> listarApenasNome();
}
