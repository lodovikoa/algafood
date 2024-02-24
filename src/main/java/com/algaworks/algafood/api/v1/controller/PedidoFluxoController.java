package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.domain.service.PedidoFluxoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pedidos/{pedidoCodigo}")
public class PedidoFluxoController {

    @Autowired
    private PedidoFluxoService pedidoFluxoService;

    @Transactional
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmar(@PathVariable String pedidoCodigo) {
        pedidoFluxoService.confirmar(pedidoCodigo);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregar(@PathVariable String pedidoCodigo) {
        pedidoFluxoService.entregar(pedidoCodigo);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar(@PathVariable String pedidoCodigo) {
        pedidoFluxoService.cancelar(pedidoCodigo);
        return ResponseEntity.noContent().build();
    }
}
