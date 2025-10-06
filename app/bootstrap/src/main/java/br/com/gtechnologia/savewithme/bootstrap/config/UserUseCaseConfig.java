package br.com.gtechnologia.savewithme.bootstrap.config;


import br.com.gtechnologia.savewithme.adapters.out.persistence.UserRepositoryAdapter;
import br.com.gtechnologia.savewithme.adapters.out.persistence.repository.SpringDataUserRepository;
import br.com.gtechnologia.savewithme.application.ports.in.useCases.UserUseCase;
import br.com.gtechnologia.savewithme.application.ports.out.provider.TokenProvider;
import br.com.gtechnologia.savewithme.application.ports.out.util.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("!gen")
public class UserUseCaseConfig {
    @Bean
    UserRepositoryAdapter userRepositoryAdapter(SpringDataUserRepository repo) {
        return new UserRepositoryAdapter(repo);
    }

    @Bean
    UserUseCase registerUserUseCase(UserRepositoryAdapter repo, PasswordHasher hasher, TokenProvider tokenProvider) {
        return new UserUseCase(repo, hasher, tokenProvider);
    }
}
