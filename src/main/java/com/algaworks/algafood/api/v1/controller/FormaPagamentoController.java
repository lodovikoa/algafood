package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelDTOAssembler;
import com.algaworks.algafood.api.v1.dto.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.v1.dto.model.FormaPagamentoModelDTO;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.util.concurrent.TimeUnit;

;

@RestController
@RequestMapping("/v1/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoModelDTOAssembler formaPagamentoModelDTOAssembler;

    @Autowired
    private FormaPagamentoInputDTODisassembler formaPagamentoInputDTODisassembler;


    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModelDTO>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";
        var dataUltimaAtualizacao = formaPagamentoService.getDataUltimaAtualizacao();

        if(dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if(request.checkNotModified(eTag)) {
            return null;
        }

        var formaPagamentoTodos = formaPagamentoService.listar();
        var formasPagamentoModelDTO = formaPagamentoModelDTOAssembler.toCollectionModel(formaPagamentoTodos);

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formasPagamentoModelDTO);
    }


    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoModelDTO> buscarPorId(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";
        var dataAtualizacao = formaPagamentoService.getDataUltimaAtualizacaoId(formaPagamentoId);

        if(dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if(request.checkNotModified(eTag)) {
            return null;
        }

        var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
        var formaPagamentoModelDTO = formaPagamentoModelDTOAssembler.toModel(formaPagamento);

        return ResponseEntity
                .ok()
                // .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                // .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
                // .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .cacheControl(CacheControl.noCache()) // Faz cache porém confirma todas as requisisões para verificar se houve mudança
                // .cacheControl(CacheControl.noStore()) // Não faz nenhum cache
                .eTag(eTag)
                .body(formaPagamentoModelDTO);
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
