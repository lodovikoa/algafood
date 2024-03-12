package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.dto.model.CozinhaModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelDTOAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public CozinhaModelDTOAssembler() {
        super(CozinhaController.class, CozinhaModelDTO.class);
    }

    @Override
    public CozinhaModelDTO toModel(Cozinha cozinha) {
        var cozinhaModelDTO = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModelDTO);

        if(algaSecurity.podeConsultarCozinhas()) {
            cozinhaModelDTO.add(algaLinks.linkToCozinha("cozinhas"));
        }

        return cozinhaModelDTO;
    }

}
