package br.com.gtechnologia.savewithme.adapters.in.web.rest.dto;

import java.util.UUID;

public record LoginResponse(String token, UUID userId, String email, String username) {
}
