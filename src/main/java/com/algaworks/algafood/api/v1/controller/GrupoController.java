package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.GrupoInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.GrupoModelDTOAssembler;
import com.algaworks.algafood.api.v1.dto.input.GrupoInputDTO;
import com.algaworks.algafood.api.v1.dto.model.GrupoModelDTO;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/grupos")
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoService grupoService;
    @Autowired
    private GrupoModelDTOAssembler grupoModelDTOAssembler;

    @Autowired
    private GrupoInputDTODisassembler grupoInputDTODisassembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<GrupoModelDTO> listar() {
        var todosGrupos = grupoService.listar();
        return grupoModelDTOAssembler.toCollectionModel(todosGrupos);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{idGrupo}")
    public GrupoModelDTO buscarPorId(@PathVariable Long idGrupo) {
        var grupo = grupoService.buscarOuFalhar(idGrupo);
        return grupoModelDTOAssembler.toModel(grupo);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModelDTO cadastrar(@Valid @RequestBody GrupoInputDTO grupoInputDTO) {
        var grupo = grupoInputDTODisassembler.toDomaninObject(grupoInputDTO);
        grupo = grupoService.salvar(grupo);
        return grupoModelDTOAssembler.toModel(grupo);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Transactional
    @PutMapping("/{grupoId}")
    public GrupoModelDTO alterar(@Valid @RequestBody GrupoInputDTO grupoInputDTO, @PathVariable Long grupoId) {
        var grupoOriginal = grupoService.buscarOuFalhar(grupoId);
        grupoInputDTODisassembler.copyDomainObject(grupoInputDTO, grupoOriginal);
        var grupo = grupoService.salvar(grupoOriginal);
        return grupoModelDTOAssembler.toModel(grupo);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @DeleteMapping("{grupoId}")
    public void excluir(@PathVariable Long grupoId) {
        grupoService.excluir(grupoId);
    }

}
