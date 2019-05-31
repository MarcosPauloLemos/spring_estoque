package br.com.ithappens.mapper;

import br.com.ithappens.model.Estoque;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EstoqueMapper {

    Estoque recuperarEstoque(@Param("filialId") Long filialId, @Param("produtoId") Long produtoId);

    void upsert(@Param("estoque") Estoque estoque);

    void inserirEstoque(@Param("estoque") Estoque estoque);

    void atualizarEstoque(@Param("estoque") Estoque estoque);
}
