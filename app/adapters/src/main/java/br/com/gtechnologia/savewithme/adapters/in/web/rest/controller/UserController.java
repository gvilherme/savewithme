package br.com.gtechnologia.savewithme.adapters.in.web.rest.controller;

import br.com.gtechnologia.savewithme.adapters.in.web.rest.dto.LoginRequest;
import br.com.gtechnologia.savewithme.adapters.in.web.rest.dto.LoginResponse;
import br.com.gtechnologia.savewithme.adapters.in.web.rest.dto.RegisterUserRequest;
import br.com.gtechnologia.savewithme.adapters.in.web.rest.dto.RegisterUserResponse;
import br.com.gtechnologia.savewithme.application.ports.in.commands.AuthenticateUserCommand;
import br.com.gtechnologia.savewithme.application.ports.in.commands.RegisterUserCommand;
import br.com.gtechnologia.savewithme.application.ports.in.useCases.UserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest req){
        var id = userUseCase.handleRegistration(new RegisterUserCommand(req.username(), req.email(), req.password()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(id));
    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest req){
        var res = userUseCase.handleAuthentication(new AuthenticateUserCommand(req.username(), req.email(), req.password()));
        return new LoginResponse(res.token(), res.userId(), res.email(), res.username());
    }

    @GetMapping("/me")
    public Map<String, Object> me(@RequestAttribute("userId") UUID userId,
                                  @RequestAttribute("email") String email) {
        return Map.of("userId", userId, "email", email);
    }
}
