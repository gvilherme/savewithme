package br.com.gtechnologia.savewithme.adapters.out.persistence.entity;

import br.com.gtechnologia.domain.model.Email;
import br.com.gtechnologia.domain.model.UserId;
import br.com.gtechnologia.domain.model.UserName;
import br.com.gtechnologia.savewithme.adapters.out.persistence.util.EmailConverter;
import br.com.gtechnologia.savewithme.adapters.out.persistence.util.UserIdConverter;
import br.com.gtechnologia.savewithme.adapters.out.persistence.util.UserNameConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @Convert(converter = UserIdConverter.class)
    UserId id;
    @Column(unique = true)
    @Convert(converter = EmailConverter.class)
    Email email;
    @Column(unique = true, length = 32)
    @Convert(converter = UserNameConverter.class)
    UserName username;
    @Column(name = "password_hash", length = 64)
    String passwordHash;
}
