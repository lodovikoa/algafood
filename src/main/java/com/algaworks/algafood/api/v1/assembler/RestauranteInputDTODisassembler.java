package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.dto.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDTODisassembler {

    @Autowired
    ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInputDTO restauranteInputDTO) {
        return modelMapper.map(restauranteInputDTO, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInputDTO restauranteInputDTO, Restaurante restaurante) {
        // Para evitar org.hibernate.HibernagteException:identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        if(restaurante.getEndereco() != null)
            restaurante.getEndereco().setCidade(new Cidade());
        modelMapper.map(restauranteInputDTO, restaurante);
    }
}
