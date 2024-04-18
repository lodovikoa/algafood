package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.model.FotoProdutoModelDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Fotos-produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    @Operation(summary = "Busca a foto do produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoModelDTO.class)),
                    @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                    @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
            })
    })
    public FotoProdutoModelDTO buscar(Long restauranteId, Long produtoId);

    @Operation(hidden = true)
    public ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader);
}
