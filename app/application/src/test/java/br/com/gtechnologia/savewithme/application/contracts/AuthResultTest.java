package br.com.gtechnologia.savewithme.application.contracts;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthResultTest {
    @Test
    void constructorAndGettersShouldWork() {
        String token = "token123";
        UUID userId = UUID.randomUUID();
        String email = "user@example.com";
        String username = "user";

        AuthResult result = new AuthResult(token, userId, email, username);

        assertEquals(token, result.token());
        assertEquals(userId, result.userId());
        assertEquals(email, result.email());
        assertEquals(username, result.username());
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        String token = "token123";
        UUID userId = UUID.randomUUID();
        String email = "user@example.com";
        String username = "user";

        AuthResult r1 = new AuthResult(token, userId, email, username);
        AuthResult r2 = new AuthResult(token, userId, email, username);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void toStringShouldContainFieldValues() {
        String token = "token123";
        UUID userId = UUID.randomUUID();
        String email = "user@example.com";
        String username = "user";

        AuthResult result = new AuthResult(token, userId, email, username);

        String str = result.toString();
        assertTrue(str.contains(token));
        assertTrue(str.contains(userId.toString()));
        assertTrue(str.contains(email));
        assertTrue(str.contains(username));
    }
}