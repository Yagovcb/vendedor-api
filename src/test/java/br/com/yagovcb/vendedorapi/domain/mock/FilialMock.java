package br.com.yagovcb.vendedorapi.domain.mock;

import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;
import br.com.yagovcb.vendedorapi.domain.model.Filial;

import java.time.LocalDate;
import java.util.ArrayList;

public class FilialMock {

    public static Filial getFilialMock(){
        return Filial.builder()
                .id(1L)
                .nome("Empresa de teste 1")
                .cnpj("83317843000143")
                .cidade("Belém")
                .uf("PA")
                .tipo(TipoFilial.DEPOSITO)
                .ativo(Boolean.TRUE)
                .dataCadastro(LocalDate.now())
                .ultimaAtualizacao(LocalDate.now())
                .vendedores(new ArrayList<>())
                .build();
    }
}
