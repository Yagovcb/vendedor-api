package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.mock.FilialMock;
import br.com.yagovcb.vendedorapi.domain.mock.TokenMock;
import br.com.yagovcb.vendedorapi.domain.mock.UsuarioMock;
import br.com.yagovcb.vendedorapi.domain.mock.VendedorMock;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.model.Token;
import br.com.yagovcb.vendedorapi.domain.model.Usuario;
import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DisplayName("Teste da classe de repository UsuarioRepository")
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setup(){
        Usuario usuario = UsuarioMock.getUser_withoutRoleMock();
        usuario.setId(null);
        Usuario usuarioRole = UsuarioMock.getUser_RoleMock();
        usuarioRole.setId(null);
        usuarioRepository.saveAll(List.of(usuario, usuarioRole));
    }

    @Test
    @DisplayName("Teste do método findByDocumento do repository")
    void findByUsernameTest(){
        var username = UsuarioMock.getUser_withoutRoleMock().getUsername();
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);
        assertTrue(optionalUsuario.isPresent());
        assertNotNull(optionalUsuario.get());
        var usuario = optionalUsuario.get();
        assertEquals(username, usuario.getUsername());

    }
}
