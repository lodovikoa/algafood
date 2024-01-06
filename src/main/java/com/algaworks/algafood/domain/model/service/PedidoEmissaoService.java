package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoEmissaoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private FormaPagamentoService formaPagamentoService;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido emitir(Pedido pedido) {
        this.validarPedido(pedido);
        this.validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    private void validarPedido(Pedido pedido) {
        var cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        var cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
        var restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        var formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceitaq por esse restaurante", formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(itemPedido -> {
            var produto = produtoService.buscarOuFalhar(pedido.getRestaurante().getId(), itemPedido.getProduto().getId());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setPrecoUnitario(produto.getPreco());
        });
    }


}
