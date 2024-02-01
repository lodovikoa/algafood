package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.PedidoFluxoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoCodigo}")
public class PedidoFluxoController {

    @Autowired
    private PedidoFluxoService pedidoFluxoService;

    @Transactional
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String pedidoCodigo) {
        pedidoFluxoService.confirmar(pedidoCodigo);
    }

    @Transactional
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String pedidoCodigo) {
        pedidoFluxoService.entregar(pedidoCodigo);
    }

    @Transactional
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String pedidoCodigo) {
        pedidoFluxoService.cancelar(pedidoCodigo);
    }
}
