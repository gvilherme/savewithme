package br.com.gtechnologia.savewithme.adapters.out.persistence.util;

import br.com.gtechnologia.domain.model.UserName;
import jakarta.persistence.AttributeConverter;

public class UserNameConverter implements AttributeConverter<UserName, String> {
    @Override
    public String convertToDatabaseColumn(UserName userName) {
        return userName.value();
    }

    @Override
    public UserName convertToEntityAttribute(String s) {
        return new UserName(s);
    }
}
