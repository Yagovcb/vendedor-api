package br.com.yagovcb.vendedorapi.infrastructure.integration.utils;

import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.infrastructure.integration.response.FilialResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FilialUtils {

    public static FilialResponse montaFilialResponse(Filial filial) {
        return FilialResponse.builder()
                .cnpj(filial.getCnpj())
                .nome(filial.getNome())
                .tipo(filial.getTipo())
                .build();
    }
}
