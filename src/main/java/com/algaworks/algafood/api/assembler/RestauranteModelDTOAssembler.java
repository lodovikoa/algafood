package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.RestauranteModelDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelDTOAssembler {

    @Autowired
    ModelMapper modelMapper;

    public RestauranteModelDTO toModelDTO(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteModelDTO.class);
    }

    public List<RestauranteModelDTO> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModelDTO(restaurante))
                .collect(Collectors.toList());
    }
}
