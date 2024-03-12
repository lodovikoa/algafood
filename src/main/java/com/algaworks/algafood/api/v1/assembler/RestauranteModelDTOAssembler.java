package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.dto.model.RestauranteModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModelDTO> {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteModelDTOAssembler() {
        super(RestauranteController.class, RestauranteModelDTO.class);
    }

    @Override
    public RestauranteModelDTO toModel(Restaurante restaurante) {
        var restauranteModelDTO = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModelDTO);

        if(algaSecurity.podeConsultarRestaurantes()) {
            restauranteModelDTO.add(algaLinks.linkToRestaurante("restaurantes"));
        }

        if(algaSecurity.podeConsultarRestaurantes()) {
            restauranteModelDTO.add(algaLinks.linkToRestauranteFormasPagamento(restauranteModelDTO.getId(), "formas-pagamento"));
        }

        if(algaSecurity.podeGerenciarCadastroRestaurantes()) {
            restauranteModelDTO.add(algaLinks.linkToRestauranteResponsaveis(restauranteModelDTO.getId(), "responsaveis"));
        }

        if(algaSecurity.podeConsultarRestaurantes()) {
            restauranteModelDTO.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
        }

        if(algaSecurity.podeGerenciarCadastroRestaurantes()) {
            if (restaurante.ativacaoPermitida())
                restauranteModelDTO.add(algaLinks.linkToRestauranteAtivacao(restauranteModelDTO.getId(), "Ativar"));

            if (restaurante.inativacaoPermitida())
                restauranteModelDTO.add(algaLinks.linkToRestauranteInativacao(restauranteModelDTO.getId(), "Inativar"));
        }

        if(algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.aberturaPermitida())
                restauranteModelDTO.add(algaLinks.linkToRestauranteAbertura(restauranteModelDTO.getId(), "abrir"));

            if (restaurante.fechamentoPermitido()) {
                restauranteModelDTO.add(algaLinks.linkToRestauranteFechamento(restauranteModelDTO.getId(), "fechar"));
            }
        }

        if(algaSecurity.podeConsultarCozinhas()) {
            restauranteModelDTO.getCozinha().add(algaLinks.linkToCozinha(restauranteModelDTO.getCozinha().getId()));
        }

        if(algaSecurity.podeConsultarCidades()) {
            if (restauranteModelDTO.getEndereco() != null && restauranteModelDTO.getEndereco().getCidade() != null) {
                restauranteModelDTO.getEndereco().getCidade().add(algaLinks.linkToCidade(restauranteModelDTO.getEndereco().getCidade().getId()));
            }
        }

        return restauranteModelDTO;
    }

    @Override
    public CollectionModel<RestauranteModelDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if(algaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(algaLinks.linkToRestaurante());
        }

        return collectionModel;
    }
}
