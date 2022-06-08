package com.example.ProjetoTabela.exceptions;

import com.example.ProjetoTabela.utils.i18n.GenericTranslator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiException extends RuntimeException {

    protected final HttpStatus status;

    public ApiException(String code, GenericTranslator translator, HttpStatus status) {
        this(status, translator.toLocale(code));
    }

    public ApiException(String code, GenericTranslator translator, HttpStatus status, Object... args) {
        this(status, translator.toLocale(code, args));
    }

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ApiException(HttpStatus status, Throwable throwable) {
        super(throwable);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
