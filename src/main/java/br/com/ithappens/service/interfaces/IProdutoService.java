package br.com.ithappens.service.interfaces;

import br.com.ithappens.model.Produto;

public interface IProdutoService {
    Produto recuperarProdutoPorId(Long produtoId);
}
