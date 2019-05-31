package br.com.ithappens.model;

import br.com.ithappens.model.enums.StatusPedido;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ToString(exclude = "itens")
public class PedidoEstoque {
    private Long id;
    private Filial filial;
    private Long codCliente;
    private LocalDateTime data;
    private int statusId;
    private Boolean isSaida;
    private List<ItemPedidoEstoque> itens = new ArrayList<>();

    public StatusPedido getStatus() {
        return StatusPedido.toEnum(statusId);
    }

    public void setStatus(StatusPedido status) {
        this.statusId = status.getId();
    }

    public void adicionarItem(Produto produto, BigDecimal quantidade) {

        ItemPedidoEstoque item = itens.stream().filter( it -> it.getProduto().equals(produto))
                .findAny().orElse(null);

        if(item == null){
            item = new ItemPedidoEstoque();
            item.setPedidoEstoque(this);
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setValorUnitario(produto.getPrecoUnitario());
            itens.add(item);
        }else{
            item.adicionarQuantidade(quantidade);
        }
    }
}
