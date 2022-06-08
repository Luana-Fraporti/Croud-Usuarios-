package com.example.ProjetoTabela.utils.i18n;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public abstract class GenericTranslator {

    public String toLocale(String code, Object... args) {
        final Locale locale = LocaleContextHolder.getLocale();
        return toLocaleDefault(code, locale, args);
    }

    public String toLocaleDefault(String code, Locale locale, Object... args) {
        return source().getMessage(code, args, locale);
    }

    protected abstract MessageSource source();
}
