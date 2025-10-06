package br.com.gtechnologia.savewithme.adapters.out.persistence.util;

import br.com.gtechnologia.domain.model.UserId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter
public class UserIdConverter implements AttributeConverter<UserId, UUID> {
    @Override
    public UUID convertToDatabaseColumn(UserId userId) {
        return userId.value();
    }

    @Override
    public UserId convertToEntityAttribute(UUID uuid) {
        return new UserId(uuid);
    }
}
