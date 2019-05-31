package br.com.ithappens.service.interfaces;

import br.com.ithappens.model.Estoque;
import br.com.ithappens.model.Filial;
import br.com.ithappens.model.PedidoEstoque;
import br.com.ithappens.model.Produto;

import java.math.BigDecimal;

public interface IEstoqueService {
    void processarPedidoEstoque(PedidoEstoque pedido);

    void entradaEstoque(Filial filial, Produto produto, BigDecimal quantidade);

    void saidaEstoque(Filial filial, Produto produto, BigDecimal quantidade);

    Estoque recuperarEstoque(Filial filial, Produto produto);

    void upsertEstoque(Estoque estoque);
}
