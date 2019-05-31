package br.com.ithappens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemPedidoEstoque {
    private Long id;
    @JsonIgnore
    private PedidoEstoque pedidoEstoque;
    private Produto produto;
    private BigDecimal quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

    public void adicionarQuantidade(BigDecimal qtd) {
        quantidade = quantidade.add(qtd);
    }

    public BigDecimal getValorTotal(){
        return valorUnitario.multiply(quantidade);
    }
}
