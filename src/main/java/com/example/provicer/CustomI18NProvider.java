package com.example.provicer;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class CustomI18NProvider implements I18NProvider {

    @Override
    public List<Locale> getProvidedLocales() {
        return Arrays.asList(Locale.ENGLISH, Locale.FRENCH);
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        ResourceBundle bundle = ResourceBundle.getBundle("translations", locale);
        return bundle.getString(key);
    }
}