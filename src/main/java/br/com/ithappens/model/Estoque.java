package br.com.ithappens.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    private Long id;
    private Filial filial;
    private Produto produto;
    private BigDecimal quantidade;

    public void adicionarQuantidade(BigDecimal quantidade) {
        this.quantidade = this.quantidade.add(quantidade);
    }

    public void removerQuantidade(BigDecimal quantidade) {
        this.quantidade = this.quantidade.subtract(quantidade);
    }
}
