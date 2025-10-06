package br.com.gtechnologia.savewithme.application.ports.in.useCases;

import br.com.gtechnologia.domain.model.Email;
import br.com.gtechnologia.domain.model.User;
import br.com.gtechnologia.domain.model.UserName;
import br.com.gtechnologia.savewithme.application.contracts.AuthResult;
import br.com.gtechnologia.savewithme.application.ports.in.commands.AuthenticateUserCommand;
import br.com.gtechnologia.savewithme.application.ports.in.commands.RegisterUserCommand;
import br.com.gtechnologia.savewithme.application.ports.out.persistence.UserRepository;
import br.com.gtechnologia.savewithme.application.ports.out.provider.TokenProvider;
import br.com.gtechnologia.savewithme.application.ports.out.util.PasswordHasher;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserUseCase {
    private final UserRepository repo;
    private final PasswordHasher hasher;
    private final TokenProvider tokenProvider;

    public UUID handleRegistration(RegisterUserCommand command) {
        var email = new Email(command.email());
        var username = new UserName(command.username());
        repo.findByEmail(email).ifPresent(u -> {
            throw new IllegalStateException("email in use");
        });
        repo.findByUsername(username).ifPresent(u -> {
            throw new IllegalStateException("username in use");
        });
        var hash = hasher.hash(command.password());
        var saved = repo.save(new User.Builder().email(email).userName(username).passwordHash(hash).build());
        return saved.getId().value();
    }

    public AuthResult handleAuthentication(AuthenticateUserCommand command) {
        User user = null;
        if (command.email() != null && !command.email().isBlank()) {
            var email = new Email(command.email());
            user = repo.findByEmail(email).orElse(null);
        }
        else if (user == null && command.username() != null && !command.username().isBlank()) {
            var username = new UserName(command.username());
            user = repo.findByUsername(username).orElse(null);
        } else {
            throw new IllegalArgumentException("fill email or username");
        }
        if (!hasher.verify(command.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("invalid credentials");
        }
        var jwt = tokenProvider.issue(user);
        return new AuthResult(jwt, user.getId().value(), user.getEmail().value(), user.getUserName().value());
    }
}
