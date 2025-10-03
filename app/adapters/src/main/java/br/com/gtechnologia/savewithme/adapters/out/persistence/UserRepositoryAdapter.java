package br.com.gtechnologia.savewithme.adapters.out.persistence;

import br.com.gtechnologia.domain.model.Email;
import br.com.gtechnologia.domain.model.User;
import br.com.gtechnologia.domain.model.UserName;
import br.com.gtechnologia.savewithme.adapters.out.persistence.entity.UserEntity;
import br.com.gtechnologia.savewithme.adapters.out.persistence.repository.SpringDataUserRepository;
import br.com.gtechnologia.savewithme.application.ports.out.persistence.UserRepository;

import java.util.Optional;

public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    public UserRepositoryAdapter(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return springDataUserRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<User> findByUsername(UserName username) {
        return springDataUserRepository.findByUsername(username).map(this::toDomain);
    }

    @Override
    public User save(User user) {
        var entity = toEntity(user);
        var savedEntity = springDataUserRepository.save(entity);
        return toDomain(savedEntity);
    }

    public User toDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getEmail(), entity.getUsername(), entity.getPasswordHash());
    }

    public UserEntity toEntity(User domain) {
        return new UserEntity(domain.getId(), domain.getEmail(), domain.getUserName(), domain.getPasswordHash());
    }
}
