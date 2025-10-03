package br.com.gtechnologia.domain.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class User {

    private final UserId id;
    private final Email email;
    private final UserName userName;
    private final String passwordHash;

    public User(UserId id, Email email, UserName username, String passwordHash) {
        this.id = Objects.requireNonNull(id);
        this.email = Objects.requireNonNull(email);
        this.userName = Objects.requireNonNull(username);
        this.passwordHash = Objects.requireNonNull(passwordHash);
    }

    public static class Builder {
        private Email email;
        private String passwordHash;
        private UserName userName;

        public Builder email(String email) {
            this.email = new Email(email);
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder userName(String userName) {
            this.userName= new UserName(userName);
            return this;
        }

        public Builder userName(UserName userName) {
            this.userName= userName;
            return this;
        }

        public User build() {
            return new User(UserId.generate(), email, userName, passwordHash);
        }
    }
}
