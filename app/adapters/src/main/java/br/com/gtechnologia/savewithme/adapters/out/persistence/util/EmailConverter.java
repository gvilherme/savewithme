package br.com.gtechnologia.savewithme.adapters.out.persistence.util;

import br.com.gtechnologia.domain.model.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EmailConverter implements AttributeConverter<Email, String> {
    @Override
    public String convertToDatabaseColumn(Email email) {
        return email.value();
    }

    @Override
    public Email convertToEntityAttribute(String s) {
        return new Email(s);
    }
}

