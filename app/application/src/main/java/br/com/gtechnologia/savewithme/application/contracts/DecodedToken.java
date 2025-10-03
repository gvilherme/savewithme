package br.com.gtechnologia.savewithme.application.contracts;

import br.com.gtechnologia.domain.model.User;
import br.com.gtechnologia.savewithme.application.ports.out.provider.TokenProvider;

import java.time.Instant;

public record DecodedToken(User id, String token, TokenProvider tokenProvider, Instant expiresAt, Instant issuedAt,
                           String[] scopes) {
    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean hasScope(String scope) {
        for (String s : scopes) {
            if (s.equals(scope)) {
                return true;
            }
        }
        return false;
    }

    public String refresh() {
        return tokenProvider.issue(id);
    }
}
