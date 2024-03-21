package br.com.yagovcb.vendedorapi.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CadastroVendedorStatusTest {

    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        CadastroVendedorStatus actualCadastroVendedorStatus = new CadastroVendedorStatus();
        actualCadastroVendedorStatus.setId(1L);
        actualCadastroVendedorStatus.setMensagem("Mensagem");
        actualCadastroVendedorStatus.setStatus("Status");
        actualCadastroVendedorStatus.setDocumentoVendedor("Documento Vendedor");
        String actualDocumentoVendedor = actualCadastroVendedorStatus.getDocumentoVendedor();
        Long actualId = actualCadastroVendedorStatus.getId();
        String actualMensagem = actualCadastroVendedorStatus.getMensagem();

        // Assert that nothing has changed
        assertEquals("Documento Vendedor", actualDocumentoVendedor);
        assertEquals("Mensagem", actualMensagem);
        assertEquals("Status", actualCadastroVendedorStatus.getStatus());
        assertEquals(1L, actualId.longValue());
    }

    @Test
    void testGettersAndSetters2() {
        // Arrange and Act
        CadastroVendedorStatus actualCadastroVendedorStatus = new CadastroVendedorStatus(1L, "Status", "Documento Vendedor",
                "Mensagem");
        actualCadastroVendedorStatus.setId(1L);
        actualCadastroVendedorStatus.setMensagem("Mensagem");
        actualCadastroVendedorStatus.setStatus("Status");
        actualCadastroVendedorStatus.setDocumentoVendedor("Documento Vendedor");
        String actualDocumentoVendedor = actualCadastroVendedorStatus.getDocumentoVendedor();
        Long actualId = actualCadastroVendedorStatus.getId();
        String actualMensagem = actualCadastroVendedorStatus.getMensagem();

        // Assert that nothing has changed
        assertEquals("Documento Vendedor", actualDocumentoVendedor);
        assertEquals("Mensagem", actualMensagem);
        assertEquals("Status", actualCadastroVendedorStatus.getStatus());
        assertEquals(1L, actualId.longValue());
    }
}
