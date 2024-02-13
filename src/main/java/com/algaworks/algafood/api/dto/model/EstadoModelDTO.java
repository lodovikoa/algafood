package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class EstadoModelDTO extends RepresentationModel<EstadoModelDTO> {
    private Long id;
    private String nome;
}
