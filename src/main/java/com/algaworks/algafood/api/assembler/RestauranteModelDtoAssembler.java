package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.RestauranteInputDTO;
import com.algaworks.algafood.api.dto.model.CozinhaModelDTO;
import com.algaworks.algafood.api.dto.model.RestauranteModelDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelDtoAssembler {

    @Autowired
    ModelMapper modelMapper;

    public RestauranteModelDTO toModelDTO(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteModelDTO.class);

//        CozinhaModelDTO cozinhaModelDTO = new CozinhaModelDTO(
//                restaurante.getCozinha().getId(),
//                restaurante.getCozinha().getNome()
//        );
//
//        RestauranteModelDTO restauranteModelDTO = new RestauranteModelDTO(
//                restaurante.getId(),
//                restaurante.getNome(),
//                restaurante.getTaxaFrete(),
//                cozinhaModelDTO
//        );
//
//        return restauranteModelDTO;
    }

    public List<RestauranteModelDTO> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModelDTO(restaurante))
                .collect(Collectors.toList());
    }
}
