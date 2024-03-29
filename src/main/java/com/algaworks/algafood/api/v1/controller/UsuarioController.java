package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.UsuarioInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelDTOAssembler;
import com.algaworks.algafood.api.v1.dto.input.UsuarioComSenhaInputDTO;
import com.algaworks.algafood.api.v1.dto.input.UsuarioInputDTO;
import com.algaworks.algafood.api.v1.dto.input.UsuarioSenhaInputDTO;
import com.algaworks.algafood.api.v1.dto.model.UsuarioModelDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelDTOAssembler usuarioModelDTOAssembler;

    @Autowired
    private UsuarioInputDTODisassembler usuarioInputDisassembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<UsuarioModelDTO> listar() {
        var usuarios = usuarioService.listar();
        return usuarioModelDTOAssembler.toCollectionModel(usuarios);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{usuarioId}")
    public UsuarioModelDTO buscarPorId(@PathVariable Long usuarioId) {
        var usuario = usuarioService.buscarOuFalhar(usuarioId);
        return usuarioModelDTOAssembler.toModel(usuario);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @DeleteMapping("/{usuarioId}")
    public void excluir(@PathVariable Long usuarioId) {
        usuarioService.excluir(usuarioId);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UsuarioModelDTO cadastrar(@RequestBody @Valid UsuarioComSenhaInputDTO usuarioInputDTO) {
        var usuario = usuarioInputDisassembler.toDomainObject(usuarioInputDTO);
        usuario = usuarioService.salvar(usuario);

        return usuarioModelDTOAssembler.toModel(usuario);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarusuario
    @Transactional
    @PutMapping("/{usuarioId}")
    public UsuarioModelDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
        var usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        usuarioInputDisassembler.copyDomainObject(usuarioInputDTO, usuarioAtual);
        usuarioAtual = usuarioService.salvar(usuarioAtual);

        return usuarioModelDTOAssembler.toModel(usuarioAtual);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{usuarioId}/senha")
    public void alterarSenha(@PathVariable Long usuarioId,@RequestBody @Valid UsuarioSenhaInputDTO usuarioSenhaInputDTO) {
        usuarioService.alterarSenha(usuarioId,usuarioSenhaInputDTO.getSenhaAtual(),usuarioSenhaInputDTO.getNovaSenha());
    }
}
