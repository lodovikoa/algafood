package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.repository.PermissaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext  // Injeta EntityManager
    private EntityManager manager;

    @Override
    public List<Permissao> listar() {
        return manager.createQuery("from Permissao", Permissao.class).getResultList();
    }

    @Override
    public Permissao buscar(Long id) {
        return manager.find(Permissao.class, id);
    }

    @Transactional
    @Override
    public Permissao salvar(Permissao permissao) {
        return manager.merge(permissao);
    }

    @Transactional
    @Override
    public void remover(Permissao permissao) {
        permissao = this.buscar(permissao.getId());
        manager.remove(permissao);
    }
}
