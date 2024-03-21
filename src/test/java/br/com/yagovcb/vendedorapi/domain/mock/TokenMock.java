package br.com.yagovcb.vendedorapi.domain.mock;

import br.com.yagovcb.vendedorapi.domain.model.Token;
import br.com.yagovcb.vendedorapi.domain.model.Usuario;

public class TokenMock {

    public static Token getTokenMock(Usuario usuario){
        return Token.builder()
                .id(1L)
                .token("Token")
                .tokenType("BAREAR")
                .revoked(true)
                .expired(true)
                .user(usuario)
                .build();
    }
}
