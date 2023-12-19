package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeInputDTODisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.CidadeInputDTO;
import com.algaworks.algafood.api.dto.model.CidadeModelDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeModelDTOAssembler cidadeModelDTOAssembler;

    @Autowired
    private CidadeInputDTODisassembler cidadeInputDTODisassembler;

    @GetMapping
    public List<CidadeModelDTO> listar() {
        var todasCidades = cidadeService.listar();
        return cidadeModelDTOAssembler.toCollectionModel(todasCidades);
    }

    @GetMapping("{cidadeId}")
    public CidadeModelDTO buscar(@PathVariable Long cidadeId) {
        var cidade = cidadeService.buscarOuFalhar(cidadeId);
        return cidadeModelDTOAssembler.toModel(cidade);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModelDTO salvar(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            var cidade = cidadeInputDTODisassembler.toDomainObject(cidadeInputDTO);
            cidade = cidadeService.salvar(cidade);

            return cidadeModelDTOAssembler.toModel(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw  new NegocioException(e.getMessage(), e);
        }
    }

    @Transactional
    @PutMapping("{cidadeId}")
    public CidadeModelDTO alterar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            var cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
            cidadeInputDTODisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);
            cidadeAtual = cidadeService.salvar(cidadeAtual);
            return cidadeModelDTOAssembler.toModel(cidadeAtual);
        }catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Transactional
    @DeleteMapping("{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.remover(cidadeId);
    }

}
