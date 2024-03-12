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

    public boolean gerenciaRestaurante(Long restauranteId) {

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

    public boolean isAutenticado() {
        return getAuthentication().isAuthenticated();
    }

    public boolean temEscopoEscrita() {
        return hasAuthority("SCOPE_WRITE");
    }

    public boolean temEscopoLeitura() {
        return hasAuthority("SCOPE_READ");
    }

    public boolean podeConsultarRestaurantes() {
        return temEscopoLeitura() && isAutenticado();
    }

    public boolean podeGerenciarCadastroRestaurantes() {
        return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTE");
    }

    public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
        return temEscopoEscrita() && (hasAuthority("EDITAR_RESTAURANTES") || gerenciaRestaurante(restauranteId));
    }

    public boolean podeConsultarUsuariosGruposPermissoes() {
        return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPO_PERMISSOES");
    }

    public boolean podeEditarUsuariosGruposPermissoes() {
        return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_PERMISSOES");
    }

    public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
        return temEscopoLeitura() && (hasAuthority("CONSULTAR_PEDIDOS") || usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId));
    }

    public boolean podePesquisarPedidos() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarFormasPagamento() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarCidades() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarEstados() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarCozinhas() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarEstatisticas() {
        return temEscopoLeitura() && hasAuthority("GERAR_RELATORIOS");
    }
}
