package com.algaworks.algafood.api.dto.input;

import jakarta.validation.constraints.NotNull;

public record CozinhaInputIdDTO(
        @NotNull
        Long id)
{ }
