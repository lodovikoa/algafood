package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    public static final String MSG_USUARIO_EM_USU = "Usuário com ID %d não pode ser removida, pois está em uso.";

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listar () {
        return usuarioRepository.findAll();
    }

    public Usuario salvar (Usuario usuario) {
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

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }
}
