package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaInputDTODisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.CozinhaInputDTO;
import com.algaworks.algafood.api.dto.model.CozinhaModelDTO;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaModelDTOAssembler cozinhaModelDTOAssembler;

    @Autowired
    private CozinhaInputDTODisassembler cozinhaInputDTODisassembler;

    @GetMapping
    public Page<CozinhaModelDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
        var cozinhasPage = cozinhaRepository.findAll(pageable);
        var cozinhasModel = cozinhaModelDTOAssembler.toCollectionModel(cozinhasPage.getContent());
        Page<CozinhaModelDTO> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());
        return cozinhasModelPage;
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
