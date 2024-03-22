package br.com.yagovcb.vendedorapi.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import br.com.yagovcb.vendedorapi.domain.enums.TipoContracao;
import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.yagovcb.vendedorapi.domain.mock.FilialMock;
import br.com.yagovcb.vendedorapi.domain.mock.VendedorMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VendedorTest {
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Filial filial = FilialMock.getFilialMock();
        Vendedor vendedor = VendedorMock.getVendedorMock_withFilial(filial);

        // Act and Assert
        assertEquals(vendedor, vendedor);
        int expectedHashCodeResult = vendedor.hashCode();
        assertEquals(expectedHashCodeResult, vendedor.hashCode());
    }

    @Test
    void testEquals() {
        // Arrange
        Filial filial = FilialMock.getFilialMock();

        Vendedor vendedor = VendedorMock.getVendedorMock_withFilial(filial);
        Vendedor vendedorEquals = VendedorMock.getVendedorMock_withFilial(filial);
        assertNotEquals(new Vendedor(), vendedor);
        assertEquals(vendedor, vendedorEquals);
        assertTrue(vendedorEquals.equals(vendedor));
    }
    @Test
    void testToString() {
        // Arrange
        Filial filial = new Filial();
        filial.setAtivo(true);
        filial.setCidade("Cidade");
        filial.setCnpj("Cnpj");
        filial.setDataCadastro(LocalDate.of(1970, 1, 1));
        filial.setId(1L);
        filial.setNome("Nome");
        filial.setTipo(TipoFilial.DEPOSITO);
        filial.setUf("Uf");
        filial.setUltimaAtualizacao(LocalDate.of(1970, 1, 1));
        filial.setVendedores(new ArrayList<>());

        Vendedor vendedor = new Vendedor();
        vendedor.setDataNascimento(LocalDate.of(1970, 1, 1));
        vendedor.setDocumento("alice.liddell@example.org");
        vendedor.setEmail("jane.doe@example.org");
        vendedor.setFilial(filial);
        vendedor.setId(1L);
        vendedor.setMatricula("Matricula");
        vendedor.setNome("Nome");
        vendedor.setTipoContratacao(TipoContracao.OUTSOURCING);

        // Act and Assert
        assertEquals(
                "Vendedor(id = 1, matricula = Matricula, nome = Nome, dataNascimento = 1970-01-01, documento ="
                        + " alice.liddell@example.org, email = jane.doe@example.org, tipoContratacao = OUTSOURCING)",
                vendedor.toString());
    }
}
