package com.algaworks.algafood.api.v1.dto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModelDTO {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private CidadeResumoModelDTO cidade;
}

