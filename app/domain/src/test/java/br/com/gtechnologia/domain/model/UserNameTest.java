package br.com.gtechnologia.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNameTest {
    @Test
    void shouldCreateUserNameWithValidValue() {
        UserName userName = new UserName("valid_user");
        assertEquals("valid_user", userName.value());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new UserName(null));
    }

    @Test
    void shouldThrowExceptionWhenValueIsTooLong() {
        String longName = "a".repeat(32);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new UserName(longName));
        assertEquals("username", exception.getMessage());
    }

    @Test
    void shouldImplementEqualsAndHashCode() {
        UserName userName1 = new UserName("user");
        UserName userName2 = new UserName("user");
        assertEquals(userName1, userName2);
        assertEquals(userName1.hashCode(), userName2.hashCode());
    }
}