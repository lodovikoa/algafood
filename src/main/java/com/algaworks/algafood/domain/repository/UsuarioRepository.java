package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

//    @Query("select l from Usuario l where l.email = :email")
//    UserDetails findByLogin(String email);

    @Query("select count(u) > 0 from Usuario u where u.email = :email and (:id is null or u.id <> :id)")
    boolean existsByEmailAndIdNot(String email, Long id);

    @Query("select u from Usuario u join fetch u.grupos g join fetch g.permissoes  where u.email = :email")
    UserDetails findByLogin(String email);

 //  UserDetails findByEmail(String username);

}
