package br.com.gtechnologia.savewithme.application.ports.out.provider;

import br.com.gtechnologia.domain.model.User;
import br.com.gtechnologia.savewithme.application.contracts.DecodedToken;

public interface TokenProvider {
    String issue(User user); // retorna JWT
    DecodedToken verify(String token); // lança exceção se inválido/expirado
}
