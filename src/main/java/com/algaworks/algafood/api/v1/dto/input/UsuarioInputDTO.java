package com.algaworks.algafood.api.v1.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputDTO {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;
}
