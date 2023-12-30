package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.FormaPagamentoModelDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoModelDTOAssembler {

    @Autowired
    ModelMapper modelMapper;

    public FormaPagamentoModelDTO toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModelDTO.class);
    }

    public List<FormaPagamentoModelDTO> toCollectionModel(Collection<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream()
                .map(formaPagamento -> toModel(formaPagamento))
                .collect(Collectors.toList());
    }
}
