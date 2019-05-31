package br.com.ithappens.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundValidationException extends ValidationException {
    public NotFoundValidationException(String s) {
        super(s);
    }
}
