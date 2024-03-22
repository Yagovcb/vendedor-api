package br.com.yagovcb.vendedorapi.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import br.com.yagovcb.vendedorapi.domain.mock.TokenMock;
import br.com.yagovcb.vendedorapi.domain.mock.UsuarioMock;
import org.junit.jupiter.api.Test;

class TokenTest {

    @Test
    void testEquals() {
        // Arrange
        Usuario user = UsuarioMock.getUsuarioWithFullRoleMock();

        Token token = TokenMock.getTokenMock(user);

        // Act and Assert
        assertNotEquals(token, null);
    }

    /**
     * Method under test: {@link Token#equals(Object)}
     */
    @Test
    void testEqualsFullCoverage() {
        // Arrange
        Usuario user = UsuarioMock.getUsuarioWithFullRoleMock();

        Token token = TokenMock.getTokenMock(user);
        Token tokenEquals = TokenMock.getTokenMock(user);

        assertNotEquals(new Token(), token);
        assertEquals(token, tokenEquals);
        assertTrue(tokenEquals.equals(token));

    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Usuario user = UsuarioMock.getUsuarioWithFullRoleMock();

        Token token = TokenMock.getTokenMock(user);

        // Act and Assert
        assertEquals(token, token);
        int expectedHashCodeResult = token.hashCode();
        assertEquals(expectedHashCodeResult, token.hashCode());
    }

}
