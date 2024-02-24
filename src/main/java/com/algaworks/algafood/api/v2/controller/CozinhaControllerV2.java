package com.algaworks.algafood.api.v2.controller;


import com.algaworks.algafood.api.v2.assembler.CozinhaInputDTODisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CozinhaModelDTOAssemblerV2;
import com.algaworks.algafood.api.v2.dto.input.CozinhaInputDTOV2;
import com.algaworks.algafood.api.v2.dto.model.CozinhaModelDTOV2;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/v2/cozinhas"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaModelDTOAssemblerV2 cozinhaModelDTOAssembler;

    @Autowired
    private CozinhaInputDTODisassemblerV2 cozinhaInputDTODisassembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaModelDTOV2> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaModelDTOV2> cozinhasPagedModel = pagedResourcesAssembler
                .toModel(cozinhasPage, cozinhaModelDTOAssembler);

        return cozinhasPagedModel;
    }


    @GetMapping("/{cozinhaId}")
    public CozinhaModelDTOV2 buscar(@PathVariable Long cozinhaId) {
        var cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        return cozinhaModelDTOAssembler.toModel(cozinha);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelDTOV2 salvar(@RequestBody @Valid CozinhaInputDTOV2 cozinhaInputDTO) {
        var cozinha = cozinhaInputDTODisassembler.toDomainObject(cozinhaInputDTO);
        cozinha = cozinhaService.salvar(cozinha);
        return cozinhaModelDTOAssembler.toModel(cozinha);
    }

    @PutMapping(value = "/{cozinhaId}")
    public CozinhaModelDTOV2 atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputDTOV2 cozinhaInputDTO) {
        var cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);
        cozinhaInputDTODisassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);
        cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
        return cozinhaModelDTOAssembler.toModel(cozinhaAtual);
    }

    @Transactional
    @DeleteMapping(value = "/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.excluir(cozinhaId);
    }
}
