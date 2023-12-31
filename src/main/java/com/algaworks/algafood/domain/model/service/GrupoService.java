package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {

    private static final String MSG_GRUPO_EM_USO = "Grupo com código %d não pode ser removido, pois está em uso";
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private PermissaoService permissaoService;

    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public void excluir(Long grupoId) {
       try{
           this.buscarOuFalhar(grupoId);

           grupoRepository.deleteById(grupoId);
           grupoRepository.flush();
       } catch (EmptyResultDataAccessException e) {
           throw new GrupoNaoEncontradoException(grupoId);
       } catch (DataIntegrityViolationException e) {
           throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
       }
    }

    public Grupo buscarOuFalhar(Long idGrupo) {
        return grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new GrupoNaoEncontradoException(idGrupo));
    }

    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        var grupo = this.buscarOuFalhar(grupoId);
        var permissao = permissaoService.buscarOuFalhar(permissaoId);
        grupo.removerPermissao(permissao);
    }

    public void associarPermissao(Long grupoId, Long permissaoId) {
        var grupo = this.buscarOuFalhar(grupoId);
        var permissao = permissaoService.buscarOuFalhar(permissaoId);
        grupo.adicionarPermissao(permissao);
    }
}
