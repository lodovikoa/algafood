package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.dto.model.FormaPagamentoModelDTO;
import com.algaworks.algafood.api.utility.AlgaLinks;
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

    public FormaPagamentoModelDTOAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoModelDTO.class);
    }

    @Override
    public FormaPagamentoModelDTO toModel(FormaPagamento formaPagamento) {
        var formaPagamentoModelDTO = createModelWithId(formaPagamento.getId(), formaPagamento);
        modelMapper.map(formaPagamento, formaPagamentoModelDTO);

        formaPagamentoModelDTO.add(algaLinks.linkToFormasPagamento("formasPagamento"));

        return formaPagamentoModelDTO;
    }

    @Override
    public CollectionModel<FormaPagamentoModelDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToFormasPagamento());
    }

    //    public List<FormaPagamentoModelDTO> toCollectionModel(Collection<FormaPagamento> formaPagamentos) {
//        return formaPagamentos.stream()
//                .map(formaPagamento -> toModel(formaPagamento))
//                .collect(Collectors.toList());
//    }
}
