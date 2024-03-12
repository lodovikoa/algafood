package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.dto.model.FormaPagamentoModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoModelDTOAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public FormaPagamentoModelDTOAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoModelDTO.class);
    }

    @Override
    public FormaPagamentoModelDTO toModel(FormaPagamento formaPagamento) {
        var formaPagamentoModelDTO = createModelWithId(formaPagamento.getId(), formaPagamento);
        modelMapper.map(formaPagamento, formaPagamentoModelDTO);

        if(algaSecurity.podeConsultarFormasPagamento()) {
            formaPagamentoModelDTO.add(algaLinks.linkToFormasPagamento("formasPagamento"));
        }

        return formaPagamentoModelDTO;
    }

    @Override
    public CollectionModel<FormaPagamentoModelDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if(algaSecurity.podeConsultarFormasPagamento()) {
            collectionModel.add(algaLinks.linkToFormasPagamento());
        }

        return collectionModel;
    }
}
