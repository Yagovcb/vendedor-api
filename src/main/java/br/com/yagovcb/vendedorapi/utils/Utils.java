package br.com.yagovcb.vendedorapi.utils;

import br.com.yagovcb.vendedorapi.application.dto.TokenDTO;
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

    public static String formatarCNPJ(String cnpj) {
        return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }

    public static String desformatarDocumento(String cnpj) {
        cnpj = cnpj.replace(".", "")
                .replace("/", "")
                .replace("-", "");
        return cnpj;
    }
}
