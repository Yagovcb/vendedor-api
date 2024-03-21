package br.com.yagovcb.vendedorapi.application.controller;

import br.com.yagovcb.vendedorapi.application.service.AuthenticationServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import br.com.yagovcb.vendedorapi.application.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
public class AuthenticationController {

    private final AuthenticationServices authenticationServices;

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDTO> singIn(@RequestBody AccountCredentialsDTO accountCredentialsDTO) {
        return authenticationServices.signIn(accountCredentialsDTO);
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authenticationServices.refreshToken(request, response);
    }
}
