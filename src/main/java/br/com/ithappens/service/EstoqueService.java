package br.com.ithappens.service;

import br.com.ithappens.mapper.EstoqueMapper;
import br.com.ithappens.model.Estoque;
import br.com.ithappens.model.Filial;
import br.com.ithappens.model.PedidoEstoque;
import br.com.ithappens.model.Produto;
import br.com.ithappens.service.interfaces.IEstoqueService;
import br.com.ithappens.service.processarPedido.ProcessarPedidoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EstoqueService implements IEstoqueService {

    @Autowired
    private EstoqueMapper estoqueMapper;

    @Autowired
    private ProcessarPedidoFactory processarPedidoFactory;

    @Override
    public void processarPedidoEstoque(PedidoEstoque pedido) {
        processarPedidoFactory.definirEstrategia(pedido).executarMovimentacao();
    }

    @Override
    public void entradaEstoque(Filial filial, Produto produto, BigDecimal quantidade) {
        filial = Optional.ofNullable(filial).orElseThrow(() -> new ValidationException("Filial inválida"));
        produto = Optional.ofNullable(produto).orElseThrow(() -> new ValidationException("Produto inválido"));
        if(quantidade.compareTo(BigDecimal.ZERO) != 1){
            throw new ValidationException("Quantidade inválida!");
        }

        Estoque estoque = recuperarEstoque(filial,produto);
        estoque.adicionarQuantidade(quantidade);
        upsertEstoque(estoque);
    }

    public void upsertEstoque(Estoque estoque) {
        estoque = Optional.ofNullable(estoque).orElseThrow(() -> new ValidationException("Estoque inválido!"));
        if(estoque.getId() == null || (estoque.getId().compareTo(0L) != 1)){
            estoqueMapper.inserirEstoque(estoque);
        }else{
            estoqueMapper.atualizarEstoque(estoque);
        }
    }

    @Override
    public Estoque recuperarEstoque(Filial filial, Produto produto) {
        filial = Optional.ofNullable(filial).orElseThrow(() -> new ValidationException("Filial inválida!"));
        produto = Optional.ofNullable(produto).orElseThrow(() -> new ValidationException("Produto inválido!"));

        Estoque estoque = estoqueMapper.recuperarEstoque(filial.getId(),produto.getId());
        if(!Optional.ofNullable(estoque).isPresent()){
            estoque = new Estoque();
            estoque.setFilial(filial);
            estoque.setProduto(produto);
            estoque.setQuantidade(BigDecimal.ZERO);
        }

        return estoque;
    }

    @Override
    public void saidaEstoque(Filial filial, Produto produto, BigDecimal quantidade) {
        filial = Optional.ofNullable(filial).orElseThrow(() -> new ValidationException("Filial inválida"));
        produto = Optional.ofNullable(produto).orElseThrow(() -> new ValidationException("Produto inválido"));
        if(quantidade.compareTo(BigDecimal.ZERO) != 1){
            throw new ValidationException("Quantidade inválida!");
        }

        Estoque estoque = estoqueMapper.recuperarEstoque(filial.getId(),produto.getId());
        estoque = Optional.ofNullable(estoque).orElseThrow(() -> new ValidationException("Produto sem estoque!"));

        if(estoque.getQuantidade().compareTo(quantidade) < 0){
            throw new ValidationException("Produto não contem estoque o suficiente para o produto");
        }

        estoque.removerQuantidade(quantidade);
        upsertEstoque(estoque);
    }
}
