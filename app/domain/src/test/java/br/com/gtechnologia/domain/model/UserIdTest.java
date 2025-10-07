package br.com.gtechnologia.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {
    @Test
    void shouldCreateUserIdWithValidUUID() {
        UUID uuid = UUID.randomUUID();
        UserId userId = new UserId(uuid);
        assertEquals(uuid, userId.value());
    }

    @Test
    void shouldThrowExceptionWhenUUIDIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new UserId(null));
        assertTrue(exception.getMessage().contains("value must be a non-null UUID-v4"));
    }

    @Test
    void shouldGenerateUserIdWithRandomUUID() {
        UserId userId1 = UserId.generate();
        UserId userId2 = UserId.generate();
        assertNotNull(userId1.value());
        assertNotNull(userId2.value());
        assertNotEquals(userId1, userId2); // Should be different
    }

    @Test
    void shouldImplementEqualsAndHashCode() {
        UUID uuid = UUID.randomUUID();
        UserId userId1 = new UserId(uuid);
        UserId userId2 = new UserId(uuid);
        assertEquals(userId1, userId2);
        assertEquals(userId1.hashCode(), userId2.hashCode());
    }
}