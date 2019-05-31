package br.com.ithappens.service.interfaces;

import br.com.ithappens.model.ItemPedidoEstoque;
import br.com.ithappens.model.PedidoEstoque;

import java.math.BigDecimal;
import java.util.List;

public interface IPedidoEstoqueService {
    PedidoEstoque criarPedidoEstoque(Long filialId, Long codigoCliente, Boolean isSaida);

    void upsertPedido(PedidoEstoque pedidoEstoque);

    PedidoEstoque criarItemPedido(Long pedidoId, Long produtoId, BigDecimal quantidade);

    void inserirItemPedido(ItemPedidoEstoque item);

    PedidoEstoque recuperarPedidoPorId(Long pedidoId);

    PedidoEstoque recuperarPedidoCompleto(Long pedidoId);

    void cancelarPedido(Long pedidoId);

    void processarPedido(Long pedidoId);

    void inserirPedido(PedidoEstoque pedidoEstoque);

    void upsertItensPedidoWithList(List<ItemPedidoEstoque> itens);
}
