package com.algaworks.algafood.domain.model.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    /* Containing --> Similar ao Like %nome% */
    List<Cozinha> findTodasByNomeContaining(String nome);
    Optional<Cozinha> findCozinhaByNome(String nome);
    Boolean existsByNome(String nome);

}
