package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.model.RestauranteModelDTO;
import com.algaworks.algafood.api.utility.AlgaLinks;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModelDTO> {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteModelDTOAssembler() {
        super(RestauranteController.class, RestauranteModelDTO.class);
    }

    @Override
    public RestauranteModelDTO toModel(Restaurante restaurante) {
        var restauranteModelDTO = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModelDTO);

        restauranteModelDTO.add(algaLinks.linkToRestaurante("restaurantes"));
        restauranteModelDTO.add(algaLinks.linkToRestauranteFormasPagamento(restauranteModelDTO.getId(), "formas-pagamento"));
        restauranteModelDTO.add(algaLinks.linkToRestauranteResponsaveis(restauranteModelDTO.getId(), "responsaveis"));

        if(restaurante.ativacaoPermitida())
            restauranteModelDTO.add(algaLinks.linkToRestauranteAtivacao(restauranteModelDTO.getId(), "Ativar"));

        if(restaurante.inativacaoPermitida())
            restauranteModelDTO.add(algaLinks.linkToRestauranteInativacao(restauranteModelDTO.getId(), "Inativar"));

        if(restaurante.aberturaPermitida())
            restauranteModelDTO.add(algaLinks.linkToRestauranteAbertura(restauranteModelDTO.getId(), "abrir"));

        if(restaurante.fechamentoPermitido()) {
            restauranteModelDTO.add(algaLinks.linkToRestauranteFechamento(restauranteModelDTO.getId(), "fechar"));
        }


        restauranteModelDTO.getCozinha().add(algaLinks.linkToCozinha(restauranteModelDTO.getCozinha().getId()));
        restauranteModelDTO.getEndereco().getCidade().add(algaLinks.linkToCidade(restauranteModelDTO.getEndereco().getCidade().getId()));


        return restauranteModelDTO;
    }

    @Override
    public CollectionModel<RestauranteModelDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToRestaurante());
    }
}
