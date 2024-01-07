package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.service.PedidoFluxoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class PedidoFluxoController {

    @Autowired
    private PedidoFluxoService pedidoFluxoService;

    @Transactional
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable Long pedidoId) {
        pedidoFluxoService.confirmar(pedidoId);
    }

    @Transactional
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable Long pedidoId) {
        pedidoFluxoService.entregar(pedidoId);
    }

    @Transactional
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long pedidoId) {
        pedidoFluxoService.cancelar(pedidoId);
    }
}
