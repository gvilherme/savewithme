package br.com.gtechnologia.savewithme.application.ports.in.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticateUserCommandTest {
    @Test
    void constructorShouldSetFieldsCorrectly() {
        var command = new AuthenticateUserCommand("user", "email@example.com", "password123");
        assertEquals("user", command.username());
        assertEquals("email@example.com", command.email());
        assertEquals("password123", command.password());
    }

    @Test
    void constructorShouldThrowIfPasswordIsNull() {
        assertThrows(NullPointerException.class, () ->
                new AuthenticateUserCommand("user", "email@example.com", null)
        );
    }

    @Test
    void constructorShouldThrowIfUsernameAndEmailAreBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new AuthenticateUserCommand("", "   ", "password123")
        );
        assertThrows(IllegalArgumentException.class, () ->
                new AuthenticateUserCommand(null, null, "password123")
        );
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        var c1 = new AuthenticateUserCommand("user", "email@example.com", "password123");
        var c2 = new AuthenticateUserCommand("user", "email@example.com", "password123");
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void toStringShouldContainFieldValues() {
        var command = new AuthenticateUserCommand("user", "email@example.com", "password123");
        var str = command.toString();
        assertTrue(str.contains("user"));
        assertTrue(str.contains("email@example.com"));
        assertTrue(str.contains("password123"));
    }
}