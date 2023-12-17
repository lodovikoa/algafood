package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.CidadeModelDTO;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeModelDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeModelDTO toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModelDTO.class);
    }

    public List<CidadeModelDTO> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }
}
