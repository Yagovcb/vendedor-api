package br.com.yagovcb.vendedorapi.application.service;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.InvalidJwtAuthenticationException;
import br.com.yagovcb.vendedorapi.application.exceptions.UserNotFoundException;
import br.com.yagovcb.vendedorapi.domain.model.Token;
import br.com.yagovcb.vendedorapi.domain.model.Usuario;
import br.com.yagovcb.vendedorapi.domain.repository.TokenRepository;
import br.com.yagovcb.vendedorapi.domain.repository.UsuarioRepository;
import br.com.yagovcb.vendedorapi.application.dto.*;
import br.com.yagovcb.vendedorapi.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServices {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO accountCredentialsDTO) {
            var username = accountCredentialsDTO.getUsername();
            var password = accountCredentialsDTO.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Optional<Usuario> usuarioCadastrado = usuarioRepository.findByUsername(username);
            if (usuarioCadastrado.isPresent()) {
                var jwtToken = jwtService.generateToken(usuarioCadastrado.get());
                var refreshToken = jwtService.generateRefreshToken(usuarioCadastrado.get());
                revokeAllUserTokens(usuarioCadastrado.get());
                saveUserToken(usuarioCadastrado.get(), jwtToken);
                return ResponseEntity.ok(Utils.getTokenDTOBuilder(jwtToken, refreshToken, accountCredentialsDTO.getUsername()));
            } else {
                throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "User com username: " + username + ", não encontrado!");
            }
    }

    public ResponseEntity<TokenDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidJwtAuthenticationException(APIExceptionCode.INVALID_CREDENTIALS, "Token não existe ou está incorreto");
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = usuarioRepository.findByUsername(username).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                return ResponseEntity.ok(Utils.getTokenDTOBuilder(accessToken, refreshToken, username));
            }
        }
        return ResponseEntity.noContent().build();
    }

    private void revokeAllUserTokens(Usuario user) {
        Optional<List<Token>> validUserTokens = Optional.ofNullable(tokenRepository.findAllValidTokenByUser(user.getId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.get().forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens.get());
    }

    private void saveUserToken(Usuario user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType("BEARER")
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

}
