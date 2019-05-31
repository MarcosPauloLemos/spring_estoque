package br.com.ithappens.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    private Long id;
    private String descricao;
    private BigDecimal precoUnitario;
}
