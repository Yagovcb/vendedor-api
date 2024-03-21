package br.com.yagovcb.vendedorapi.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.yagovcb.vendedorapi.application.dto.AccountCredentialsDTO;
import br.com.yagovcb.vendedorapi.application.dto.TokenDTO;
import br.com.yagovcb.vendedorapi.application.exceptions.InvalidJwtAuthenticationException;
import br.com.yagovcb.vendedorapi.application.exceptions.UserNotFoundException;
import br.com.yagovcb.vendedorapi.domain.mock.TokenMock;
import br.com.yagovcb.vendedorapi.domain.mock.UsuarioMock;
import br.com.yagovcb.vendedorapi.domain.model.Token;
import br.com.yagovcb.vendedorapi.domain.model.Usuario;
import br.com.yagovcb.vendedorapi.domain.repository.TokenRepository;
import br.com.yagovcb.vendedorapi.domain.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationServices.class, AuthenticationManager.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("AuthenticationServices - Classe de teste unitario")
class AuthenticationServicesTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationServices authenticationServices;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    @Order(1)
    @DisplayName(value = "Teste cenario Sucess método SignIn")
    void testSignIn() {
        var username = "admTeste";
        AccountCredentialsDTO accountCredentialsDTO = new AccountCredentialsDTO(username, "passwordTeste");

        Optional<Usuario> ofResult = Optional.of(new Usuario());
        when(usuarioRepository.findByUsername(username)).thenReturn(ofResult);

        ResponseEntity<TokenDTO> responseEntity = authenticationServices.signIn(accountCredentialsDTO);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(2)
    @DisplayName(value = "Teste cenario - UserNotFound -  método SignIn")
    void testSingIn_userNotFound() {
        var nome = "janedoe";
        // Arrange and Act
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> authenticationServices.signIn(new AccountCredentialsDTO(nome, any())));
        assertTrue(userNotFoundException.getMessage().contains(nome));
        assertEquals("User com username: janedoe, não encontrado!", userNotFoundException.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName(value = "Teste cenario - InvalidJwtAuthentication -  método RefreshToken")
    void testRefreshToken_invalidJwtAuthentication() {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Act and Assert
        InvalidJwtAuthenticationException invalidJwtAuthenticationException = assertThrows(InvalidJwtAuthenticationException.class,
                () -> authenticationServices.refreshToken(request, new Response()));
        assertEquals("Token não existe ou está incorreto", invalidJwtAuthenticationException.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName(value = "Teste cenario - Cobertura método revokeAllUserTokens - método RefreshToken")
    void testRefreshToken_revokeAllUserTokens() {
        // Arrange
        Usuario usuario = UsuarioMock.getUser_RoleMock();
        Optional<Usuario> ofResult = Optional.of(usuario);
        when(usuarioRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);

        Usuario user = UsuarioMock.getUser_withoutRoleMock();

        Token token = TokenMock.getTokenMock(user);

        Usuario user2 = UsuarioMock.getUser_RoleMock();

        Token token2 = TokenMock.getTokenMock(user2);

        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(token2);
        when(tokenRepository.save(any())).thenReturn(token);
        when(tokenRepository.findAllValidTokenByUser(any())).thenReturn(tokenList);
        when(tokenRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(jwtService.generateToken(any())).thenReturn("ABC123");
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);
        when(jwtService.extractUsername(any())).thenReturn("janedoe");
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getHeader(any())).thenReturn("Bearer ");

        // Act
        ResponseEntity<TokenDTO> actualRefreshTokenResult = authenticationServices.refreshToken(request, new Response());

        // Assert
        verify(jwtService).extractUsername("");
        verify(jwtService).generateToken(any());
        verify(jwtService).isTokenValid(eq(""), any());
        verify(tokenRepository).findAllValidTokenByUser(any());
        verify(usuarioRepository).findByUsername("janedoe");
        verify(request).getHeader("Authorization");
        verify(tokenRepository).save(Mockito.<Token>any());
        verify(tokenRepository).saveAll(Mockito.<Iterable<Token>>any());
        TokenDTO body = actualRefreshTokenResult.getBody();
        assertEquals("", body.getRefreshToken());
        assertEquals("ABC123", body.getAccessToken());
        assertEquals("janedoe", body.getUsername());
        assertEquals(200, actualRefreshTokenResult.getStatusCode().value());
        assertTrue(actualRefreshTokenResult.hasBody());
        assertTrue(actualRefreshTokenResult.getHeaders().isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName(value = "Teste cenario - Sucess - método RefreshToken")
    void testRefreshToken() {
        // Arrange
        Usuario usuario = UsuarioMock.getUser_withoutRoleMock();
        Optional<Usuario> ofResult = Optional.of(usuario);
        when(usuarioRepository.findByUsername(any())).thenReturn(ofResult);
        when(jwtService.isTokenValid(any(), any())).thenReturn(false);
        when(jwtService.extractUsername(any())).thenReturn("janedoe");
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getHeader(any())).thenReturn("Bearer ");

        // Act
        ResponseEntity<TokenDTO> actualRefreshTokenResult = authenticationServices.refreshToken(request, new Response());

        // Assert
        verify(jwtService).extractUsername("");
        verify(jwtService).isTokenValid(eq(""), any());
        verify(usuarioRepository).findByUsername("janedoe");
        verify(request).getHeader("Authorization");
        assertNull(actualRefreshTokenResult.getBody());
        assertEquals(204, actualRefreshTokenResult.getStatusCode().value());
        assertTrue(actualRefreshTokenResult.getHeaders().isEmpty());
    }

}
