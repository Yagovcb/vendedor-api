package br.com.yagovcb.vendedorapi.utils;

import br.com.yagovcb.vendedorapi.application.dto.TokenDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static TokenDTO getTokenDTOBuilder(String accessToken, String refreshToken, String username){
        return TokenDTO.builder()
                .username(username)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static Object validaNaoNulo(Object o1, Object o2){
        return Objects.nonNull(o1) ? o1 : o2;
    }

}
