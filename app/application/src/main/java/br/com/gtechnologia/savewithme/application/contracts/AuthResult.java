package br.com.gtechnologia.savewithme.application.contracts;

import java.util.UUID;

public record AuthResult(String token, UUID userId, String email, String username) {
}
