package br.com.yagovcb.vendedorapi.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;

import java.time.LocalDate;
import java.util.ArrayList;

import br.com.yagovcb.vendedorapi.domain.mock.FilialMock;
import br.com.yagovcb.vendedorapi.utils.Utils;
import org.junit.jupiter.api.Test;

class FilialTest {

  @Test
  void testGetCnpj() {
    // Arrange
    Filial filial = FilialMock.getFilialMock();
    filial.setVendedores(new ArrayList<>());

    // Act and Assert
    assertEquals(Utils.formatarCNPJ("83317843000143"), filial.getCnpj());
  }

  @Test
  void testSetCnpj() {
    // Arrange
    Filial filial = new Filial();
    // Act
    filial.setCnpj("Cnpj");
    // Assert
    assertEquals("Cnpj", filial.getCnpj());
  }

  @Test
  void testEqualsAndHashCode() {
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

    // Act and Assert
    assertEquals(filial, filial);
    int expectedHashCodeResult = filial.hashCode();
    assertEquals(expectedHashCodeResult, filial.hashCode());
  }


  @Test
  void testEquals() {
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

    // Act and Assert
    assertNotEquals(filial, null);
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

    // Act and Assert
    assertEquals("Filial(id = 1, nome = Nome, cnpj = Cnpj, cidade = Cidade, uf = Uf, tipo = DEPOSITO, ativo = true,"
            + " dataCadastro = 1970-01-01, ultimaAtualizacao = 1970-01-01)", filial.toString());
  }
}
