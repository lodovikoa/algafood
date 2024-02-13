package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeInputDTODisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.CidadeInputDTO;
import com.algaworks.algafood.api.dto.model.CidadeModelDTO;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.api.utility.ResorceUriHelper;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeModelDTOAssembler cidadeModelDTOAssembler;

    @Autowired
    private CidadeInputDTODisassembler cidadeInputDTODisassembler;


    @GetMapping
    public CollectionModel<CidadeModelDTO> listar() {
        var todasCidades = cidadeService.listar();
        var cidadesModelDTO = cidadeModelDTOAssembler.toCollectionModel(todasCidades);

        cidadesModelDTO.forEach(cidadeModelDTO -> {
            cidadeModelDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(cidadeModelDTO.getId())).withSelfRel());
            cidadeModelDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).listar()).withRel("cidades"));

            cidadeModelDTO.getEstado().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).buscar(cidadeModelDTO.getEstado().getId())).withSelfRel());
        });


        CollectionModel<CidadeModelDTO> cidadesCollectionModelDTOS = CollectionModel.of(cidadesModelDTO);
        cidadesCollectionModelDTOS.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).listar()).withSelfRel());

        return cidadesCollectionModelDTOS;
    }

    @GetMapping("{cidadeId}")
    public CidadeModelDTO buscar(@PathVariable Long cidadeId) {
        var cidade = cidadeService.buscarOuFalhar(cidadeId);
        var cidadeModelDTO = cidadeModelDTOAssembler.toModel(cidade);

        cidadeModelDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(cidadeModelDTO.getId())).withSelfRel());
        cidadeModelDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).listar()).withRel("cidades"));

        cidadeModelDTO.getEstado().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).buscar(cidadeModelDTO.getEstado().getId())).withSelfRel());

        return cidadeModelDTO;
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModelDTO salvar(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            var cidade = cidadeInputDTODisassembler.toDomainObject(cidadeInputDTO);
            cidade = cidadeService.salvar(cidade);

            var cidadeModelDTO = cidadeModelDTOAssembler.toModel(cidade);
            ResorceUriHelper.addUriInResponseHeader(cidadeModelDTO.getId());

            return cidadeModelDTO;
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
