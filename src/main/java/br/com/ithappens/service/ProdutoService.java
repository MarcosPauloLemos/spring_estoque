package br.com.ithappens.service;

import br.com.ithappens.model.Produto;
import br.com.ithappens.service.interfaces.IProdutoService;
import br.com.ithappens.mapper.ProdutoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Optional;

@Service
public class ProdutoService implements IProdutoService {

    @Autowired
    private ProdutoMapper produtoMapper;

    @Override
    public Produto recuperarProdutoPorId(Long produtoId) {
        produtoId = Optional.ofNullable(produtoId)
                .orElseThrow(() -> new ValidationException("Produto inválido!"));

        return Optional.ofNullable(produtoMapper.recuperarProdutoPorId(produtoId))
                .orElseThrow(() -> new ValidationException("Produto não encontrado!"));

    }
}
