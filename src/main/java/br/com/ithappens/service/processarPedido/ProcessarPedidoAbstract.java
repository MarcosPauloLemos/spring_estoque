package br.com.ithappens.service.processarPedido;

import br.com.ithappens.model.PedidoEstoque;
import br.com.ithappens.service.EstoqueService;
import br.com.ithappens.service.PedidoEstoqueService;
import br.com.ithappens.service.processarPedido.interfaces.IProcessarPedido;
import org.springframework.stereotype.Service;

@Service
public abstract class ProcessarPedidoAbstract implements IProcessarPedido {

    protected final EstoqueService estoqueService;
    protected final PedidoEstoqueService pedidoEstoqueService;
    protected PedidoEstoque pedidoEstoque;

    protected ProcessarPedidoAbstract(EstoqueService estoqueService,PedidoEstoqueService pedidoEstoqueService) {
        this.estoqueService = estoqueService;
        this.pedidoEstoqueService = pedidoEstoqueService;
    }

    @Override
    public void executarMovimentacao(){
        movimentarEstoque(pedidoEstoque);
    }
}
