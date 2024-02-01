package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("select count(u) > 0 from Usuario u where u.email = :email and (:id is null or u.id <> :id)")
    boolean existsByEmailAndIdNot(String email, Long id);
}
