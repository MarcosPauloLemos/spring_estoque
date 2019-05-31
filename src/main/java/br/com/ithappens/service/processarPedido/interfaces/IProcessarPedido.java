package br.com.ithappens.service.processarPedido.interfaces;

import br.com.ithappens.model.PedidoEstoque;

public interface IProcessarPedido {
    void executarMovimentacao();

    boolean deveProcessar(Boolean isSaida);

    void movimentarEstoque(PedidoEstoque pedidoEstoque);
}
