package br.com.yagovcb.vendedorapi.utils;

import br.com.yagovcb.vendedorapi.application.dto.TokenDTO;
import br.com.yagovcb.vendedorapi.domain.model.Usuario;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {
    public static Usuario getJwtUser() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static TokenDTO getTokenDTOBuilder(String accessToken, String refreshToken, String username){
        return TokenDTO.builder()
                .username(username)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
