package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    public static final String MSG_USUARIO_EM_USU = "Usuário com ID %d não pode ser removida, pois está em uso.";

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listar () {
        return usuarioRepository.findAll();
    }

    public Usuario salvar (Usuario usuario) {

        if(usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId())) {
            throw new NegocioException(String.format("Já existe um usuário cadastrado com o Email %s", usuario.getEmail()));
        }

        if(usuario.isNovo()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return  usuarioRepository.save(usuario);
    }

    public void excluir(Long usuarioId) {
        try{
            this.buscarOuFalhar(usuarioId);

            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USU,usuarioId ));
        }
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if(!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }

    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        var usuario = this.buscarOuFalhar(usuarioId);
        var grupo = this.grupoService.buscarOuFalhar(grupoId);

        usuario.removerGrupo(grupo);
    }

    public void associarGrupo(Long usuarioId, Long grupoId) {
        var usuario = this.buscarOuFalhar(usuarioId);
        var grupo = grupoService.buscarOuFalhar(grupoId);

        usuario.adicionarGrupo(grupo);
    }
}
