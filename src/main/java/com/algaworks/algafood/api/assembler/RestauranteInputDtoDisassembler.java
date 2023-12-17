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
    }

    public void copyToDomainObject(RestauranteInputDTO restauranteInputDTO, Restaurante restaurante) {
        // Para evitar org.hibernate.HibernagteException:identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteInputDTO, restaurante);
    }
}
