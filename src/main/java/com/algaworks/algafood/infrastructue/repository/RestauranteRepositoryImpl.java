package com.algaworks.algafood.infrastructue.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.algaworks.algafood.domain.model.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.algaworks.algafood.infrastructue.repository.spec.RestauranteSpecs.comNomeSemelhante;
import static com.algaworks.algafood.infrastructue.repository.spec.RestauranteSpecs.conFreteGratis;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> findCustomizado(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        var builder = entityManager.getCriteriaBuilder();
        var criteria = builder.createQuery(Restaurante.class);

        var root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if(taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if(taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        var query = entityManager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(conFreteGratis().and(comNomeSemelhante(nome)));
    }
}
