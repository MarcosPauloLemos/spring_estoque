package br.com.ithappens.mapper;

import br.com.ithappens.model.ItemPedidoEstoque;
import br.com.ithappens.model.PedidoEstoque;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PedidoEstoqueMapper {

    PedidoEstoque recuperarPedidoPorId(@Param("pedidoId") Long pedidoId);

    PedidoEstoque recuperarPedidoCompletoPorId(@Param("pedidoId") Long pedidoId);

    void insertPedido(@Param("pedidoEstoque") PedidoEstoque pedidoEstoque);

    void upsertPedido(@Param("pedidoEstoque") PedidoEstoque pedidoEstoque);

    void upsertItemWithList(@Param("itens") List<ItemPedidoEstoque> itensPedidoEstoque);

    void inserirItemPedido(@Param("item") ItemPedidoEstoque item);

    void atualizarItemPedido(@Param("item") ItemPedidoEstoque item);

    List<ItemPedidoEstoque> recuperarItensPedidoEstoque(@Param("pedidoId") Long pedidoId);
}
