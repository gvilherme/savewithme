package br.com.gtechnologia.savewithme.application.contracts;

import br.com.gtechnologia.domain.model.User;
import br.com.gtechnologia.savewithme.application.ports.out.provider.TokenProvider;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DecodedTokenTest {
    @Test
    void constructorAndGettersShouldWork() {
        User user = mock(User.class);
        String token = "abc123";
        TokenProvider provider = mock(TokenProvider.class);
        Instant expiresAt = Instant.now().plusSeconds(3600);
        Instant issuedAt = Instant.now();
        String[] scopes = new String[] {"read", "write"};

        DecodedToken decoded = new DecodedToken(user, token, provider, expiresAt, issuedAt, scopes);

        assertEquals(user, decoded.user());
        assertEquals(token, decoded.token());
        assertEquals(provider, decoded.tokenProvider());
        assertEquals(expiresAt, decoded.expiresAt());
        assertEquals(issuedAt, decoded.issuedAt());
        assertArrayEquals(scopes, decoded.scopes());
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        User user = mock(User.class);
        TokenProvider provider = mock(TokenProvider.class);
        Instant expiresAt = Instant.now().plusSeconds(3600);
        Instant issuedAt = Instant.now();
        String[] scopes = new String[] {"read", "write"};

        DecodedToken d1 = new DecodedToken(user, "abc123", provider, expiresAt, issuedAt, scopes);
        DecodedToken d2 = new DecodedToken(user, "abc123", provider, expiresAt, issuedAt, scopes);

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void toStringShouldContainFieldValues() {
        User user = mock(User.class);
        TokenProvider provider = mock(TokenProvider.class);
        Instant expiresAt = Instant.now().plusSeconds(3600);
        Instant issuedAt = Instant.now();
        String[] scopes = new String[] {"read", "write"};

        DecodedToken decoded = new DecodedToken(user, "abc123", provider, expiresAt, issuedAt, scopes);

        String str = decoded.toString();
        assertTrue(str.contains("abc123"));
        assertTrue(str.contains(expiresAt.toString()));
        assertTrue(str.contains(issuedAt.toString()));
    }
}