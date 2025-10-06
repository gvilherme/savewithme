package br.com.gtechnologia.savewithme.bootstrap.config;

import br.com.gtechnologia.savewithme.adapters.out.provider.JwtTokenProvider;
import br.com.gtechnologia.savewithme.adapters.out.util.BCryptPasswordHasher;
import br.com.gtechnologia.savewithme.application.ports.out.provider.TokenProvider;
import br.com.gtechnologia.savewithme.application.ports.out.util.PasswordHasher;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;

@Configuration
@Profile("!gen")
public class IdentityConfig {
    private static RSAPrivateKey loadPrivate(String pem) throws Exception {
        String content = pem.replaceAll("-----\\w+ PRIVATE KEY-----", "").replaceAll("\\s", "");
        byte[] key = Base64.getDecoder().decode(content);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(key));
        // Atenção: a chave deve estar em PKCS#8
    }

    private static RSAPublicKey loadPublic(String pem) throws Exception {
        String content = pem.replaceAll("-----\\w+ PUBLIC KEY-----", "").replaceAll("\\s", "");
        byte[] key = Base64.getDecoder().decode(content);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(key));
    }

    @Bean
    PasswordHasher passwordHasher() {
        return new BCryptPasswordHasher();
    }

    @Bean
    JWKSource<SecurityContext> jwkSource(@Value("${security.jwt.private-key-pem}") String privatePem, @Value("${security.jwt.public-key-pem}") String publicPem) throws Exception {
        RSAPrivateKey privateKey = loadPrivate(privatePem);
        RSAPublicKey publicKey = loadPublic(publicPem);
        RSAKey jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID("savewithme-kid-1").build();
        return new ImmutableJWKSet<>(new JWKSet(jwk));
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    JwtDecoder jwtDecoder(@Value("${security.jwt.public-key-pem}") String publicPem, @Value("${security.jwt.issuer}") String issuer) throws Exception {

        RSAPublicKey pub = loadPublic(publicPem);
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(pub).build();

        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withTimestamps = new JwtTimestampValidator(Duration.ofSeconds(30));
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer, withTimestamps));
        return decoder;
    }

    @Bean
    TokenProvider tokenProvider(JwtEncoder encoder, JwtDecoder decoder) {
        return new JwtTokenProvider(encoder, decoder);
    }
}
