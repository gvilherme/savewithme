package br.com.gtechnologia.savewithme.adapters.out.provider;

import br.com.gtechnologia.domain.model.User;
import br.com.gtechnologia.domain.model.UserId;
import br.com.gtechnologia.savewithme.application.contracts.DecodedToken;
import br.com.gtechnologia.savewithme.application.ports.out.provider.TokenProvider;

import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.time.Duration;
import java.util.UUID;

public class JwtTokenProvider implements TokenProvider {
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public JwtTokenProvider(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public String issue(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("savewithme").subject(user.getId().value().toString()).claim("email", user.getEmail().value()).issuedAt(now).expiresAt(now.plus(Duration.ofHours(2))).build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public DecodedToken verify(String token) {
        Jwt jwt = decoder.decode(token);
        return new DecodedToken(new UserId(UUID.fromString(jwt.getSubject())), jwt.getTokenValue(), this, jwt.getExpiresAt(), jwt.getIssuedAt(), new String[]{});
    }
}
