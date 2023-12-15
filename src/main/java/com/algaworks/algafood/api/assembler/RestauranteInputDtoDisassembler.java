package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDtoDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInputDTO restauranteInputDTO) {
        return modelMapper.map(restauranteInputDTO, Restaurante.class);
//        Cozinha cozinha = new Cozinha();
//        cozinha.setId(restauranteInputDTO.cozinha().id());
//
//        Restaurante restaurante = new Restaurante();
//        restaurante.setNome(restauranteInputDTO.nome());
//        restaurante.setTaxaFrete(restauranteInputDTO.taxaFrete());
//        restaurante.setCozinha(cozinha);
//
//        return restaurante;
    }
}
