package br.com.gtechnologia.savewithme.adapters.out.provider;

import br.com.gtechnologia.domain.model.Email;
import br.com.gtechnologia.domain.model.User;
import br.com.gtechnologia.domain.model.UserId;
import br.com.gtechnologia.domain.model.UserName;
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
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("https://savewithme.app").subject(user.getId().value().toString()).claim("email", user.getEmail().value()).claim("username", user.getUserName().value()).issuedAt(now).expiresAt(now.plus(Duration.ofHours(2))).build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public DecodedToken verify(String token) {
        Jwt jwt = decoder.decode(token);

        // Check expiration
        if (jwt.getExpiresAt() != null && jwt.getExpiresAt().isBefore(Instant.now())) {
            throw new JwtException("Token expired");
        }

        // Validate issuer
        if (!"https://savewithme.app".equals(jwt.getIssuer().toString())) {
            throw new JwtException("Issuer inválido");
        }

        // Validate required claims
        String emailClaim = jwt.getClaim("email");
        String userNameClaim = jwt.getClaim("username");
        if (emailClaim == null || userNameClaim == null) {
            throw new JwtException("Missing required claims");
        }

        UserId userId = new UserId(UUID.fromString(jwt.getSubject()));
        Email email = new Email(jwt.getClaim("email"));
        UserName uName = new UserName(jwt.getClaim("username"));
        return new DecodedToken(new User(userId, email, uName, null), jwt.getTokenValue(), this, jwt.getExpiresAt(), jwt.getIssuedAt(), new String[]{});
    }
}
