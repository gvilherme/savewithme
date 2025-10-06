package br.com.gtechnologia.savewithme.application.contracts;

import br.com.gtechnologia.domain.model.User;
import br.com.gtechnologia.savewithme.application.ports.out.provider.TokenProvider;

import java.time.Instant;

public record DecodedToken(User user,
                           String token,
                           TokenProvider tokenProvider,
                           Instant expiresAt,
                           Instant issuedAt,
                           String[] scopes) {}
