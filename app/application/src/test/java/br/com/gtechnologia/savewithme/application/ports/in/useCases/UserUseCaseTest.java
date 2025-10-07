package br.com.gtechnologia.savewithme.application.ports.in.useCases;

import br.com.gtechnologia.domain.model.User;
import br.com.gtechnologia.domain.model.UserId;
import br.com.gtechnologia.domain.model.UserName;
import br.com.gtechnologia.savewithme.application.ports.in.commands.AuthenticateUserCommand;
import br.com.gtechnologia.savewithme.application.ports.in.commands.RegisterUserCommand;
import br.com.gtechnologia.savewithme.application.ports.out.persistence.UserRepository;
import br.com.gtechnologia.savewithme.application.ports.out.provider.TokenProvider;
import br.com.gtechnologia.savewithme.application.ports.out.util.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserUseCaseTest {

    public static final String USERNAME = "tester";
    public static final String EMAIL = "testMail@mail.com";
    public static final String PASSWORD = "password123";
    private UserRepository repo;
    private PasswordHasher hasher;
    private TokenProvider tokenProvider;
    @Mock
    private static final User user = Mockito.mock(User.class);

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(UserRepository.class);
        hasher = Mockito.mock(PasswordHasher.class);
        tokenProvider = Mockito.mock(TokenProvider.class);
    }

    @Test
    void handleRegistrationShouldWorkGreacefully() {
        var useCase = new UserUseCase(repo, hasher, tokenProvider);
        var command = new RegisterUserCommand(USERNAME, EMAIL, PASSWORD);
        Mockito.when(repo.save(Mockito.any())).thenReturn(user);
        var userId = new UserId(UUID.randomUUID());
        Mockito.when(user.getId()).thenReturn(userId);
        assertDoesNotThrow(() -> {
            var uuid = useCase.handleRegistration(command);
            assertEquals(userId.value(), uuid);
        });
    }

    @Test
    void handleRegistrationShouldThrowWhenEmailInUse() {
        var useCase = new UserUseCase(repo, hasher, tokenProvider);
        var command = new RegisterUserCommand(USERNAME, EMAIL, PASSWORD);
        Mockito.when(repo.findByEmail(Mockito.any())).thenReturn(java.util.Optional.of(user));
        assertThrows(IllegalStateException.class, () -> useCase.handleRegistration(command));
    }

    @Test
    void handleRegistrationShouldThrowWhenUsernameInUse() {
        var useCase = new UserUseCase(repo, hasher, tokenProvider);
        var command = new RegisterUserCommand(USERNAME, EMAIL, PASSWORD);
        Mockito.when(repo.findByUsername(Mockito.any())).thenReturn(java.util.Optional.of(user));
        assertThrows(IllegalStateException.class, () -> useCase.handleRegistration(command));
    }

    @Test
    void handleAuthenticationShouldThrowWhenNoEmailOrUsernameIsProvided() {
        var useCase = new UserUseCase(repo, hasher, tokenProvider);
        var command = new RegisterUserCommand("", "", PASSWORD);
        assertThrows(IllegalArgumentException.class, () -> useCase.handleRegistration(command));
    }

    @Test
    void handleAuthenticationShouldThrowWhenCredentialsAreInvalid() {
        var useCase = new UserUseCase(repo, hasher, tokenProvider);
        var nullEmailCommand = new AuthenticateUserCommand(USERNAME, null, PASSWORD);
        var nullUsernameCommand = new AuthenticateUserCommand(null, EMAIL, PASSWORD);
        var userNameThatDoesNotMatchCommand = new AuthenticateUserCommand("NONEXISTENT", "", PASSWORD);
        Mockito.when(repo.findByUsername(Mockito.any())).thenReturn(java.util.Optional.of(user));
        Mockito.when(repo.findByUsername(new UserName("NONEXISTENT"))).thenReturn(java.util.Optional.of(user));
        Mockito.when(hasher.verify(Mockito.any(), Mockito.any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> useCase.handleAuthentication(nullEmailCommand));
        assertThrows(IllegalArgumentException.class, () -> useCase.handleAuthentication(nullUsernameCommand));
        assertThrows(IllegalArgumentException.class, () -> useCase.handleAuthentication(userNameThatDoesNotMatchCommand));
    }

    @Test
    void handleAuthenticationShouldWorkGreacefully() {
        var useCase = new UserUseCase(repo, hasher, tokenProvider);
        var command = new AuthenticateUserCommand(null, EMAIL, PASSWORD);
        Mockito.when(repo.findByEmail(Mockito.any())).thenReturn(java.util.Optional.of(user));
        Mockito.when(hasher.verify(Mockito.any(), Mockito.any())).thenReturn(true);
        var userId = new UserId(UUID.randomUUID());
        Mockito.when(user.getId()).thenReturn(userId);
        Mockito.when(user.getEmail()).thenReturn(new br.com.gtechnologia.domain.model.Email(EMAIL));
        Mockito.when(user.getUserName()).thenReturn(new br.com.gtechnologia.domain.model.UserName(USERNAME));
        Mockito.when(user.getPasswordHash()).thenReturn(PASSWORD);
        Mockito.when(tokenProvider.issue(Mockito.any())).thenReturn("token");
        assertDoesNotThrow(() -> {
            var result = useCase.handleAuthentication(command);
            assertEquals("token", result.token());
            assertEquals(userId.value(), result.userId());
            assertEquals(EMAIL, result.email());
            assertEquals(USERNAME, result.username());
        });
    }
}