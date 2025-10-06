package br.com.gtechnologia.savewithme.adapters.out.util;

import br.com.gtechnologia.savewithme.application.ports.out.util.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordHasher implements PasswordHasher {
    private final BCryptPasswordEncoder enc = new BCryptPasswordEncoder(10);
    @Override
    public boolean verify(String rawPassword, String hashed) {
        return enc.matches(rawPassword, hashed);
    }

    @Override
    public String hash(String rawPassword) {
        return enc.encode(rawPassword);
    }
}
