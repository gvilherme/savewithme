package br.com.gtechnologia.domain.model;

import java.io.Serializable;
import java.util.Objects;

public record Email (String value) implements Serializable {
    public Email {
        Objects.requireNonNull(value);
        if(!value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) throw new IllegalArgumentException("email");
    }
}
