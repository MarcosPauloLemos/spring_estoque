package br.com.ithappens.service;

import br.com.ithappens.mapper.PedidoEstoqueMapper;
import br.com.ithappens.model.Filial;
import br.com.ithappens.model.ItemPedidoEstoque;
import br.com.ithappens.model.PedidoEstoque;
import br.com.ithappens.model.Produto;
import br.com.ithappens.model.enums.StatusPedido;
import br.com.ithappens.service.interfaces.IEstoqueService;
import br.com.ithappens.service.interfaces.IFilialService;
import br.com.ithappens.service.interfaces.IPedidoEstoqueService;
import br.com.ithappens.service.interfaces.IProdutoService;
import br.com.ithappens.utils.NotFoundValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoEstoqueService implements IPedidoEstoqueService {

    @Autowired
    private IFilialService filialService;

    @Autowired
    private PedidoEstoqueMapper pedidoEstoqueMapper;

    @Autowired
    private IProdutoService produtoService;

    @Autowired
    private IEstoqueService estoqueService;

    @Override
    public PedidoEstoque criarPedidoEstoque(Long filialId, Long codigoCliente, Boolean isSaida){

        Filial filial = filialService.recuperarPorId(filialId);
        filial.setId(filialId);

        codigoCliente = Optional.ofNullable(codigoCliente).orElseThrow(()
                -> new ValidationException("Codigo inválido"));

        isSaida = Optional.ofNullable(isSaida).orElseThrow(()
                -> new ValidationException("Saída inválida"));

        PedidoEstoque pedidoEstoque = new PedidoEstoque();
        pedidoEstoque.setFilial(filial);
        pedidoEstoque.setCodCliente(codigoCliente);
        pedidoEstoque.setStatus(StatusPedido.ATIVO);
        pedidoEstoque.setData(LocalDateTime.now());
        pedidoEstoque.setIsSaida(isSaida);

        this.inserirPedido(pedidoEstoque);
        return pedidoEstoque;
    }

    @Override
    public void upsertPedido(PedidoEstoque pedidoEstoque) {
        pedidoEstoque = Optional.ofNullable(pedidoEstoque)
                .orElseThrow(() -> new ValidationException("Pedido inválido!"));

        pedidoEstoqueMapper.upsertPedido(pedidoEstoque);
    }

    @Override
    public PedidoEstoque criarItemPedido(Long pedidoId, Long produtoId, BigDecimal quantidade) {

        quantidade = Optional.ofNullable(quantidade)
                .orElseThrow(() -> new ValidationException("Quantidade inválida!"));

        if(quantidade.compareTo(BigDecimal.ZERO) != 1) {
            throw new ValidationException("Quantidade inválida!");
        }

        PedidoEstoque pedido = recuperarPedidoCompleto(pedidoId);
        Produto produto = produtoService.recuperarProdutoPorId(produtoId);
        pedido.adicionarItem(produto,quantidade);
        upsertItensPedidoWithList(pedido.getItens());
        return pedido;
    }

    @Override
    public void upsertItensPedidoWithList(List<ItemPedidoEstoque> itens) {
        if(itens == null || itens.isEmpty())
            throw new ValidationException("Pedido sem itens!");

        for(ItemPedidoEstoque item : itens){
            if(item.getId() == null || (item.getId().compareTo(0L) != 1)){
                pedidoEstoqueMapper.inserirItemPedido(item);
            }else{
                pedidoEstoqueMapper.atualizarItemPedido(item);
            }
        }
    }

    @Override
    public void inserirItemPedido(ItemPedidoEstoque item) {
        item = Optional.ofNullable(item).orElseThrow(
                () -> new ValidationException("Item pedido inválido"));

        pedidoEstoqueMapper.inserirItemPedido(item);
    }

    @Override
    public PedidoEstoque recuperarPedidoPorId(Long pedidoId) {
        pedidoId = Optional.ofNullable(pedidoId).orElseThrow(
                () -> new ValidationException("Pedido inválido!"));

        return Optional.ofNullable(pedidoEstoqueMapper.recuperarPedidoPorId(pedidoId))
                .orElseThrow(() -> new NotFoundValidationException("Pedido não encontrado!"));
    }

    @Override
    public PedidoEstoque recuperarPedidoCompleto(Long pedidoId) {
        pedidoId = Optional.ofNullable(pedidoId).orElseThrow(
                () -> new ValidationException("Pedido inválido!"));

        PedidoEstoque pedidoEstoque = pedidoEstoqueMapper.recuperarPedidoPorId(pedidoId);
        if(Optional.ofNullable(pedidoEstoque).isPresent()){
            List<ItemPedidoEstoque> itens = pedidoEstoqueMapper.recuperarItensPedidoEstoque(pedidoId);
            if(!itens.isEmpty()){
                pedidoEstoque.setItens(itens);
            }
        }

        return Optional.ofNullable(pedidoEstoque)
                .orElseThrow(() -> new NotFoundValidationException("Pedido não encontrado!"));
    }

    @Override
    public void cancelarPedido(Long pedidoId) {
        pedidoId = Optional.ofNullable(pedidoId).orElseThrow(
                () -> new ValidationException("Pedido inválido"));

        PedidoEstoque pedido = recuperarPedidoCompleto(pedidoId);
        if(!pedido.getStatus().equals(StatusPedido.ATIVO)){
            throw new ValidationException("Status não permite cancelamento!");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoEstoqueMapper.upsertPedido(pedido);
    }

    @Override
    public void processarPedido(Long pedidoId) {
        pedidoId = Optional.ofNullable(pedidoId).orElseThrow(
                () -> new ValidationException("Pedido inválido"));

        PedidoEstoque pedido = recuperarPedidoCompleto(pedidoId);
        if(!pedido.getStatus().equals(StatusPedido.ATIVO)){
            throw new ValidationException("Status não permite processamento!");
        }

        estoqueService.processarPedidoEstoque(pedido);
    }

    @Override
    public void inserirPedido(PedidoEstoque pedidoEstoque) {
        pedidoEstoque = Optional.ofNullable(pedidoEstoque)
                .orElseThrow(() -> new ValidationException("Pedido inválido!"));

        pedidoEstoqueMapper.insertPedido(pedidoEstoque);
    }

}
