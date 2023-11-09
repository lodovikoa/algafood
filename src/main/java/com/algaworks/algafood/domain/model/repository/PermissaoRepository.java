package com.algaworks.algafood.domain.model.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> listar();
    Permissao buscar(Long id);
    Permissao salvar(Permissao permissao);
    void remover(Permissao permissao);
}
