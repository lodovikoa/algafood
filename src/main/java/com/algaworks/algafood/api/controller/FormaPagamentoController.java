package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.dto.model.FormaPagamentoModelDTO;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoModelDTOAssembler formaPagamentoModelDTOAssembler;

    @Autowired
    private FormaPagamentoInputDTODisassembler formaPagamentoInputDTODisassembler;


    @GetMapping
    public ResponseEntity<List<FormaPagamentoModelDTO>> listar() {
        var formaPagamentoTodos = formaPagamentoService.listar();
        var formasPagamentoModelDTO = formaPagamentoModelDTOAssembler.toCollectionModel(formaPagamentoTodos);

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formasPagamentoModelDTO);
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoModelDTO buscarPorId(@PathVariable Long formaPagamentoId) {
        var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
        return formaPagamentoModelDTOAssembler.toModel(formaPagamento);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModelDTO cadastrar(@Valid @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
        var formaPagamento = formaPagamentoInputDTODisassembler.toDomainObject(formaPagamentoInputDTO);
        formaPagamento = formaPagamentoService.salvar(formaPagamento);
        return formaPagamentoModelDTOAssembler.toModel(formaPagamento);
    }

    @Transactional
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoModelDTO alterar(@Valid @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO, @PathVariable Long formaPagamentoId) {
        var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
        formaPagamentoInputDTODisassembler.copyToDomainObject(formaPagamentoInputDTO, formaPagamento);

        var formaPagamentoAtual = formaPagamentoService.salvar(formaPagamento);
        return formaPagamentoModelDTOAssembler.toModel(formaPagamento);
    }

    @Transactional
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.excluir(formaPagamentoId);
    }
}
