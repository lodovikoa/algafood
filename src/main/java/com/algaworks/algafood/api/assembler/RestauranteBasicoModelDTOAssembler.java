package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.model.RestauranteBasicoModelDTO;
import com.algaworks.algafood.api.utility.AlgaLinks;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoModelDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;
    public RestauranteBasicoModelDTOAssembler() {
        super(RestauranteController.class, RestauranteBasicoModelDTO.class);
    }

    @Override
    public RestauranteBasicoModelDTO toModel(Restaurante restaurante) {
        var restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restauranteModel, restauranteModel);

        restauranteModel.add(algaLinks.linkToRestaurante("restaurantes"));
        restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getId()));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModelDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToRestaurante());
    }
}
