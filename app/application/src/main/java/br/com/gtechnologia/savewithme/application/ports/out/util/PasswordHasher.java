package br.com.gtechnologia.savewithme.application.ports.out.util;

public interface PasswordHasher {
    String hash(String password);

    boolean verify(String password, String hash);
}
