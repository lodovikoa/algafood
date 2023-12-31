package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.EstadoModelDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public EstadoModelDTO toModel(Estado estado) {
        return modelMapper.map(estado, EstadoModelDTO.class);
    }

    public List<EstadoModelDTO> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(estado -> toModel(estado))
                .collect(Collectors.toList());
    }
}
