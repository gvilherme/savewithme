package br.com.gtechnologia.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record UserId(UUID value) implements Serializable {
    public static UserId fromString(String value) {
        return new UserId(UUID.fromString(value));
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    public UserId {
        Objects.requireNonNull(value, "value must be a non-null UUID-v4");
    }
}
