package br.com.gtechnologia.domain.model;

import org.junit.jupiter.api.Test;

public class EmailTest {
    @Test
    public void shouldCreateValidEmail( ) {
        var email = new Email("test@teste.com");
        var otherEmail = new Email("test@teste.com");
        var anotherEmail = new Email("teste@test.com");
        assert email.value().equals("test@teste.com");
        assert email.equals(otherEmail);
        assert !email.equals(anotherEmail);
        assert email.hashCode() == otherEmail.hashCode();
        assert email.toString().equals("Email[value=test@teste.com]");
    }

    @Test
    public void shouldThrowExceptionForInvalidEmail( ) {
        try {
            new Email("invalid-email");
            assert false; // should not reach here
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("email");
        }
    }

    @Test
    public void shouldThrowExceptionForNullEmail( ) {
        try {
            new Email(null);
            assert false; // should not reach here
        } catch (NullPointerException e) {
            assert true; // expected
        }
    }
}
