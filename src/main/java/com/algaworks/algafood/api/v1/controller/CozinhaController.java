package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.CozinhaInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.CozinhaModelDTOAssembler;
import com.algaworks.algafood.api.v1.dto.input.CozinhaInputDTO;
import com.algaworks.algafood.api.v1.dto.model.CozinhaModelDTO;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Slf4j
@RestController
@RequestMapping(value = {"/v1/cozinhas"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaModelDTOAssembler cozinhaModelDTOAssembler;

    @Autowired
    private CozinhaInputDTODisassembler cozinhaInputDTODisassembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaModelDTO> listar(@PageableDefault(size = 10) Pageable pageable) {

        log.info("Consultando cozinhas com páginas de {} registros...",pageable.getPageSize());

        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaModelDTO> cozinhasPagedModel = pagedResourcesAssembler
                .toModel(cozinhasPage, cozinhaModelDTOAssembler);

        return cozinhasPagedModel;
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaModelDTO buscar(@PathVariable Long cozinhaId) {
        var cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        return cozinhaModelDTOAssembler.toModel(cozinha);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelDTO salvar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
        var cozinha = cozinhaInputDTODisassembler.toDomainObject(cozinhaInputDTO);
        cozinha = cozinhaService.salvar(cozinha);
        return cozinhaModelDTOAssembler.toModel(cozinha);
    }

    @PutMapping(value = "/{cozinhaId}")
    public CozinhaModelDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
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
