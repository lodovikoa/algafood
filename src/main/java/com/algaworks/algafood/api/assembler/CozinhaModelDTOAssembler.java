package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.dto.model.CozinhaModelDTO;
import com.algaworks.algafood.api.utility.AlgaLinks;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaModelDTOAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public CozinhaModelDTOAssembler() {
        super(CozinhaController.class, CozinhaModelDTO.class);
    }

    @Override
    public CozinhaModelDTO toModel(Cozinha cozinha) {
        var cozinhaModelDTO = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModelDTO);

        cozinhaModelDTO.add(algaLinks.linkToCozinha("cozinhas"));

        return cozinhaModelDTO;
    }

}
