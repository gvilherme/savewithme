package br.com.gtechnologia.domain.model;

import java.io.Serializable;
import java.util.Objects;

public record UserName (String value) implements Serializable {
    public UserName {
        Objects.requireNonNull(value);
        if(value.length() > 31) throw new IllegalArgumentException("username");
    }
}
