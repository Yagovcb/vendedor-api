package br.com.yagovcb.vendedorapi.domain.mock;

import br.com.yagovcb.vendedorapi.domain.model.CadastroVendedorStatus;

public class CadastroVendedorStatusMock {

    public static CadastroVendedorStatus getCadastroVendedorStatusMock(){
        return CadastroVendedorStatus.builder()
                .id(1L)
                .mensagem("Mensagem")
                .status("Status")
                .build();
    }

    public static CadastroVendedorStatus getCadastroVendedorStatusMockSegundoTeste(){
        return CadastroVendedorStatus.builder()
                .id(2L)
                .mensagem("Mensagem 2")
                .status("Status")
                .build();
    }
}
