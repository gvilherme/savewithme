package br.com.gtechnologia.savewithme.bootstrap.config;

import br.com.gtechnologia.savewithme.adapters.out.provider.JwtTokenProvider;
import br.com.gtechnologia.savewithme.application.ports.in.useCases.UserUseCase;
import br.com.gtechnologia.savewithme.application.ports.out.provider.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("gen")
public class StubsConfigForTest {
    @Bean
    UserUseCase registerUserUseCaseStub() {
        return new UserUseCase(null, null, null);
    }

    @Bean
    TokenProvider tokenProvider() {
        return new JwtTokenProvider(null, null);
    }
}
