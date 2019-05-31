package br.com.ithappens.mapper;

import br.com.ithappens.model.Filial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FilialMapper {

    Filial recuperarPorId(@Param("filialId") Long filialId);
}
