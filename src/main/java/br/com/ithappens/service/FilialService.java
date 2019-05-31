package br.com.ithappens.service;

import br.com.ithappens.mapper.FilialMapper;
import br.com.ithappens.model.Filial;
import br.com.ithappens.service.interfaces.IFilialService;
import br.com.ithappens.utils.NotFoundValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Optional;

@Service
public class FilialService implements IFilialService {

    @Autowired
    private FilialMapper filialMapper;

    @Override
    public Filial recuperarPorId(Long filialId) {
        filialId = Optional.ofNullable(filialId).orElseThrow(
                () -> new ValidationException("Filial inválida!"));

        return Optional.ofNullable(filialMapper.recuperarPorId(filialId))
                .orElseThrow(() -> new NotFoundValidationException("Filial não encontrada!"));
    }
}
