package br.com.ithappens.service.processarPedido;

import br.com.ithappens.model.PedidoEstoque;
import br.com.ithappens.model.enums.StatusPedido;
import br.com.ithappens.service.EstoqueService;
import br.com.ithappens.service.PedidoEstoqueService;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Optional;

@Service
public class ProcessarPedidoSaida extends ProcessarPedidoAbstract{

    protected ProcessarPedidoSaida(EstoqueService estoqueService, PedidoEstoqueService pedidoEstoqueService) {
        super(estoqueService, pedidoEstoqueService);
    }

    @Override
    public boolean deveProcessar(Boolean isSaida) {
        return isSaida;
    }

    @Override
    public void movimentarEstoque(PedidoEstoque pedidoEstoque) {
        pedidoEstoque = Optional.ofNullable(pedidoEstoque).orElseThrow(
                () -> new ValidationException("Pedido inválido"));

        PedidoEstoque finalPedidoEstoque = pedidoEstoque;
        pedidoEstoque.getItens().forEach(item -> estoqueService.saidaEstoque(finalPedidoEstoque.getFilial(),
                item.getProduto(),item.getQuantidade()));

        finalPedidoEstoque.setStatus(StatusPedido.PROCESSADO);
        pedidoEstoqueService.upsertPedido(finalPedidoEstoque);
    }
}
