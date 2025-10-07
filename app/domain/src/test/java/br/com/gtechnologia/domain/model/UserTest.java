package br.com.gtechnologia.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class UserTest {

    private static final String USERNAME = "username";
    private static final String EMAIL = "email@teste.com";
    private static final String HASHED_PASSWORD = "hashedPassword";

    @Test
    void shouldCreateUserWithBuilder() {
        try (var mocked = mockStatic(UserId.class)) {
            UserId expectedId = new UserId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
            mocked.when(UserId::generate).thenReturn(expectedId);
            User user = new User.Builder().email(EMAIL).userName(USERNAME).passwordHash(HASHED_PASSWORD).build();
            User otherUser = new User.Builder().email(EMAIL).userName(USERNAME).passwordHash(HASHED_PASSWORD).build();
            assert user.getUserName().value().equals(USERNAME);
            assert user.getEmail().value().equals(EMAIL);
            assert user.getPasswordHash().equals(HASHED_PASSWORD);
            assert user.getId().value() != null;
            assert user.equals(otherUser);
            assert user.hashCode() == otherUser.hashCode();
        }
    }

    @Test
    void shouldCreateUserWithBuilderUsingNonPrimitiveTypes() {
        try (var mocked = mockStatic(UserId.class)) {
            UserId expectedId = new UserId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
            mocked.when(UserId::generate).thenReturn(expectedId);
            User user = new User.Builder().email(new Email(EMAIL)).userName(new UserName(USERNAME)).passwordHash(HASHED_PASSWORD).build();
            User otherUser = new User.Builder().email(new Email(EMAIL)).userName(new UserName(USERNAME)).passwordHash(HASHED_PASSWORD).build();
            assert user.getUserName().value().equals(USERNAME);
            assert user.getEmail().value().equals(EMAIL);
            assert user.getPasswordHash().equals(HASHED_PASSWORD);
            assert user.getId().value() != null;
            assert user.equals(otherUser);
            assert !user.equals(null);
            assert !user.equals(new Object());
        }
    }

    @Test
    void shouldCreateUserWithAllArgsConstructor() {
        User user = new User(new UserId(UUID.fromString("4fd635c4-b105-44dd-a157-c655795aa641")), new Email(EMAIL), new UserName(USERNAME),HASHED_PASSWORD);
        User otherUser = new User(new UserId(UUID.fromString("4fd635c4-b105-44dd-a157-c655795aa641")), new Email(EMAIL), new UserName(USERNAME),HASHED_PASSWORD);
        assert user.getUserName().value().equals(USERNAME);
        assert user.getEmail().value().equals(EMAIL);
        assert user.getPasswordHash().equals(HASHED_PASSWORD);
        assert user.getId().value().equals(UUID.fromString("4fd635c4-b105-44dd-a157-c655795aa641"));
        assert user.equals(otherUser);
    }

    @Test
    void shouldThrowExceptionForNullValues() {
        try {
            new User(null, new Email(EMAIL), new UserName(USERNAME), HASHED_PASSWORD);
            fail("Expected NullPointerException for null id");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            new User(new UserId(UUID.randomUUID()), null, new UserName(USERNAME), HASHED_PASSWORD);
            fail("Expected NullPointerException for null email");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            new User(new UserId(UUID.randomUUID()), new Email(EMAIL), null, HASHED_PASSWORD);
            fail("Expected NullPointerException for null username");
        } catch (NullPointerException e) {
            // expected
        }
    }
}