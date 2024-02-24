package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.dto.model.CozinhaModelDTO;
import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controller.CozinhaControllerV2;
import com.algaworks.algafood.api.v2.dto.model.CozinhaModelDTOV2;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelDTOAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelDTOV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinks;

    public CozinhaModelDTOAssemblerV2() {
        super(CozinhaControllerV2.class, CozinhaModelDTOV2.class);
    }

    @Override
    public CozinhaModelDTOV2 toModel(Cozinha cozinha) {
        CozinhaModelDTOV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));

        return cozinhaModel;
    }

}
