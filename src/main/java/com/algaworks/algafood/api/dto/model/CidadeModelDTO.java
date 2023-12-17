package com.algaworks.algafood.api.dto.model;

import com.algaworks.algafood.domain.model.Estado;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModelDTO {
    private Long id;
    private String nome;
    private Estado estado;
}
