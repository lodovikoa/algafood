package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.service.PedidoFluxoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecurity {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private PedidoFluxoService pedidoFluxoService;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        // TODO obter o usuário do token - Provisoriamente retorna usuario 5 - manoel.loja@gmail.com

        System.out.println("PASSANDO PELO AlgaSecurity.getUsuarioId()");
        return 5L;
    }

    public boolean gerenciarRestaurante(Long restauranteId) {

        System.out.println("PASSANDO PELO AlgaSecurity.gerenciarRestaurante(Long restaurante): " + restauranteId);

        if(restauranteId ==  null) {
            return  false;
        }
        return restauranteService.existsResponsavel(restauranteId, getUsuarioId());
    }

    public boolean gerenciarRestauranteDoPedido(String codigoPedido) {
        return pedidoFluxoService.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
    }

    public boolean usuarioAutenticadoIgual(Long usuarioId) {
        return getUsuarioId() != null && usuarioId != null && getUsuarioId().equals(usuarioId);
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }


    public boolean podeGerenciarPedidos(String codigoPedido) {
        return  hasAuthority("SCOPE_WRITE") &&
                (hasAuthority("GERENCIAR_PEDIDOS") || this.gerenciarRestauranteDoPedido(codigoPedido));
    }

}
