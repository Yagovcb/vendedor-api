package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.mock.TokenMock;
import br.com.yagovcb.vendedorapi.domain.mock.UsuarioMock;
import br.com.yagovcb.vendedorapi.domain.model.Token;
import br.com.yagovcb.vendedorapi.domain.model.Usuario;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DisplayName("Teste da classe de repository TokenRepository")
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class TokenRepositoryTest {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setup(){
        Usuario usuario = UsuarioMock.getUser_withoutRoleMock();
        usuario.setId(null);
        Usuario usuarioRole = UsuarioMock.getUser_RoleMock();
        usuarioRole.setId(null);
        usuarioRepository.saveAll(List.of(usuario, usuarioRole));

        Token tokenUsuario = TokenMock.getTokenMock(usuario);
        tokenUsuario.setId(null);
        Token tokenUsuarioRole = TokenMock.getTokenMock(usuarioRole);
        tokenUsuarioRole.setId(null);

        tokenRepository.saveAll(List.of(tokenUsuario, tokenUsuarioRole));
    }

    @Test
    @DisplayName("Teste do método findAllValidTokenByUser do repository")
    void findAllValidTokenByUserTest(){
        Usuario usuario = UsuarioMock.getUser_withoutRoleMock();
        List<Token> tokenList = tokenRepository.findAllValidTokenByUser(usuario.getId());
        assertFalse(tokenList.isEmpty());
        assertNotNull(tokenList.get(0));
        assertEquals(usuario, tokenList.get(0).getUser());
    }
}
