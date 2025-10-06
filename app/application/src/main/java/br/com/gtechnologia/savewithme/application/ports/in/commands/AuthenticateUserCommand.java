package br.com.gtechnologia.savewithme.application.ports.in.commands;

import java.util.Objects;

public record AuthenticateUserCommand(String username, String email, String password) {
    public AuthenticateUserCommand {
        Objects.requireNonNull(password);
        if ((username == null || username.isBlank()) && (email == null || email.isBlank())) {
            throw new IllegalArgumentException("Username or email must be provided");
        }
    }
}
