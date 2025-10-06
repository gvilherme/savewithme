package br.com.gtechnologia.savewithme.application.ports.out.persistence;

import br.com.gtechnologia.domain.model.Email;
import br.com.gtechnologia.domain.model.User;
import br.com.gtechnologia.domain.model.UserName;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(Email email);
    Optional<User> findByUsername(UserName username);
    User save(User user);
}
