package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.UsuarioComSenhaInputDTO;
import com.algaworks.algafood.api.dto.input.UsuarioInputDTO;
import com.algaworks.algafood.api.dto.input.UsuarioSenhaInputDTO;
import com.algaworks.algafood.api.dto.model.UsuarioModelDTO;
import com.algaworks.algafood.domain.model.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelDTOAssembler usuarioModelDTOAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public List<UsuarioModelDTO> listar() {
        var usuarios = usuarioService.listar();
        return usuarioModelDTOAssembler.toCollectionModel(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModelDTO buscarPorId(@PathVariable Long usuarioId) {
        var usuario = usuarioService.buscarOuFalhar(usuarioId);
        return usuarioModelDTOAssembler.toModel(usuario);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @DeleteMapping("/{usuarioId}")
    public void excluir(@PathVariable Long usuarioId) {
        usuarioService.excluir(usuarioId);
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UsuarioModelDTO cadastrar(@RequestBody @Valid UsuarioComSenhaInputDTO usuarioInputDTO) {
        var usuario = usuarioInputDisassembler.toDomainObject(usuarioInputDTO);
        usuario = usuarioService.salvar(usuario);

        return usuarioModelDTOAssembler.toModel(usuario);
    }

    @Transactional
    @PutMapping("/{usuarioId}")
    public UsuarioModelDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
        var usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        usuarioInputDisassembler.copyDomainObject(usuarioInputDTO, usuarioAtual);
        usuarioAtual = usuarioService.salvar(usuarioAtual);

        return usuarioModelDTOAssembler.toModel(usuarioAtual);
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{usuarioId}/senha")
    public void alterarSenha(@PathVariable Long usuarioId,@RequestBody @Valid UsuarioSenhaInputDTO usuarioSenhaInputDTO) {
        usuarioService.alterarSenha(usuarioId,usuarioSenhaInputDTO.getSenhaAtual(),usuarioSenhaInputDTO.getNovaSenha());
    }
}
