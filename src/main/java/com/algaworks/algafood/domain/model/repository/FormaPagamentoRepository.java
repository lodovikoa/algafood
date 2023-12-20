package com.algaworks.algafood.domain.model.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

//    List<FormaPagamento> listar();
//    FormaPagamento buscar(Long id);
//    FormaPagamento salvar(FormaPagamento formaPagamento);
//    void remover(FormaPagamento formaPagamento);
}
