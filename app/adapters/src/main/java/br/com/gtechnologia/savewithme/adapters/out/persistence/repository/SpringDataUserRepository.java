package br.com.gtechnologia.savewithme.adapters.out.persistence.repository;

import br.com.gtechnologia.domain.model.Email;
import br.com.gtechnologia.domain.model.UserId;
import br.com.gtechnologia.domain.model.UserName;
import br.com.gtechnologia.savewithme.adapters.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, UserId> {
    Optional<UserEntity> findByEmail(Email email);
    Optional<UserEntity> findByUsername(UserName username);
}
