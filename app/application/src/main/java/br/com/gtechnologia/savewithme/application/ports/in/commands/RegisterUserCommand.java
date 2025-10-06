package br.com.gtechnologia.savewithme.application.ports.in.commands;

public record RegisterUserCommand(String username, String email, String password) {
}
