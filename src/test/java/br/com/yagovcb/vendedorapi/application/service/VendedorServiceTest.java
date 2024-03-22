package br.com.yagovcb.vendedorapi.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.IntegrationException;
import br.com.yagovcb.vendedorapi.application.exceptions.NotFoundException;
import br.com.yagovcb.vendedorapi.domain.enums.TipoContracao;
import br.com.yagovcb.vendedorapi.domain.mock.FilialMock;
import br.com.yagovcb.vendedorapi.domain.mock.VendedorMock;
import br.com.yagovcb.vendedorapi.domain.model.CadastroVendedorStatus;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import br.com.yagovcb.vendedorapi.domain.repository.VendedorRepository;
import br.com.yagovcb.vendedorapi.infrastructure.integration.response.FilialResponse;
import br.com.yagovcb.vendedorapi.infrastructure.integration.service.FilialIntegrationService;
import br.com.yagovcb.vendedorapi.infrastructure.request.AtualizaVendedorRequest;
import br.com.yagovcb.vendedorapi.infrastructure.request.CadastroVendedorRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import br.com.yagovcb.vendedorapi.infrastructure.response.VendedorResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VendedorService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("VendedorServiceTest - Classe de teste unitario")
class VendedorServiceTest {

  @MockBean
  private VendedorRepository vendedorRepository;

  @MockBean
  private FilialIntegrationService filialIntegrationService;

  @MockBean
  private CadastroVendedorStatusService cadastroVendedorStatusService;

  @Autowired
  private VendedorService vendedorService;

  @Test
  @Order(1)
  @DisplayName(value = "Teste cenario - NotFound - método FindAll")
  void testFindAll_NotFoundException() {
    // Arrange
    when(vendedorRepository.findAll()).thenReturn(new ArrayList<>());

    // Act and Assert
    NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> vendedorService.findAll());
    assertNotNull(notFoundException);
    assertEquals("Não foi encontrado nenhum vendedor na base.", notFoundException.getMessage());

  }

  @Test
  @Order(2)
  @DisplayName(value = "Teste cenario - NotFound - método FindById")
  void testFindById_WhenVendedorDoesNotExist_ThrowsNotFoundException() {
    // Arrange
    String documento = "1234567890";
    when(vendedorRepository.findByDocumento(documento)).thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> vendedorService.findByDocumento(documento));
  }

  @Test
  @Order(3)
  @DisplayName(value = "Teste cenario - Sucess - método AtualizaVendedor")
  void testAtualizaVendedor_WhenVendedorExistsAndEmailIsValid_UpdatesVendedor() {
    // Arrange
    String documento = "1234567890";
    AtualizaVendedorRequest request = new AtualizaVendedorRequest();
    request.setEmail("test@example.com");

    Vendedor vendedor = new Vendedor();
    vendedor.setDocumento(documento);
    when(vendedorRepository.findByDocumento(documento)).thenReturn(Optional.of(vendedor));

    // Act
    ResponseEntity<HttpStatus> responseEntity = vendedorService.atualizaVendedor(documento, request);

    // Assert
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    verify(vendedorRepository, times(1)).save(any(Vendedor.class));
  }

  @Test
  @Order(4)
  @DisplayName(value = "Teste cenario - NotFound - método AtualizaVendedor")
  void testAtualizaVendedor_WhenVendedorDoesNotExist_ThrowsNotFoundException() {
    // Arrange
    String documento = "1234567890";
    AtualizaVendedorRequest request = new AtualizaVendedorRequest();
    request.setEmail("test@example.com");

    when(vendedorRepository.findByDocumento(documento)).thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> vendedorService.atualizaVendedor(documento, request));
  }


  @Test
  @Order(5)
  @DisplayName(value = "Teste cenario - Sucess - método Deleta")
  void testDeleta_WhenVendedorExists_DeletesVendedor() {
    // Arrange
    String documento = "1234567890";
    Vendedor vendedor = new Vendedor();
    vendedor.setDocumento(documento);
    when(vendedorRepository.findByDocumento(documento)).thenReturn(Optional.of(vendedor));

    // Act
    ResponseEntity<HttpStatus> responseEntity = vendedorService.deleta(documento);

    // Assert
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    verify(vendedorRepository, times(1)).delete(vendedor);
  }

  @Test
  @Order(6)
  @DisplayName(value = "Teste cenario - NotFound - método Deleta")
  void testDeleta_WhenVendedorDoesNotExist_ThrowsNotFoundException() {
    // Arrange
    String documento = "1234567890";
    when(vendedorRepository.findByDocumento(documento)).thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> vendedorService.deleta(documento));
  }

  @Test
  @Order(7)
  @DisplayName(value = "Teste cenario - Sucess - método ValidaVendedorJaExistente")
  void testValidaVendedorJaExistente_WhenVendedorExists_ReturnsTrue() {
    // Arrange
    String documento = "1234567890";
    when(vendedorRepository.existsByDocumento(documento)).thenReturn(true);

    // Act
    boolean result = vendedorService.validaVendedorJaExistente(documento);

    // Assert
    assertTrue(result);
  }

  @Test
  @Order(8)
  @DisplayName(value = "Teste cenario - Sucess - método ValidaVendedorJaExistente")
  void testValidaVendedorJaExistente_WhenVendedorDoesNotExist_ReturnsFalse() {
    // Arrange
    String documento = "1234567890";
    when(vendedorRepository.existsByDocumento(documento)).thenReturn(false);

    // Act
    boolean result = vendedorService.validaVendedorJaExistente(documento);

    // Assert
    assertFalse(result);
  }

  @Test
  @Order(9)
  @DisplayName(value = "Teste cenario - Sucess - método RetornaFilialResponse")
  void testRetornaFilialResponse_WhenFilialExists_ReturnsFilialResponse() {
    // Arrange
    Long id = 1L;
    ResponseEntity<FilialResponse> responseEntity = ResponseEntity.ok(new FilialResponse());
    when(filialIntegrationService.findById(id)).thenReturn(responseEntity);

    // Act
    FilialResponse filialResponse = vendedorService.retornaFilialResponse(id);

    // Assert
    assertNotNull(filialResponse);
  }

  @Test
  @Order(10)
  @DisplayName(value = "Teste cenario - NotFound - método RetornaFilialResponse")
  void testRetornaFilialResponse_WhenFilialDoesNotExist_ThrowsNotFoundException() {
    // Arrange
    Long id = 1L;
    ResponseEntity<FilialResponse> responseEntity = ResponseEntity.notFound().build();
    when(filialIntegrationService.findById(id)).thenReturn(responseEntity);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> vendedorService.retornaFilialResponse(id));
  }

  @Test
  @Order(11)
  @DisplayName(value = "Teste cenario - Sucess - método RetornaVendedor")
  void testRetornaVendedor_WhenVendedorExists_ReturnsVendedor() {
    // Arrange
    String documento = "1234567890";
    Vendedor vendedor = new Vendedor();
    vendedor.setDocumento(documento);
    when(vendedorRepository.findByDocumento(documento)).thenReturn(Optional.of(vendedor));

    // Act
    Vendedor result = vendedorService.retornaVendedor(documento);

    // Assert
    assertNotNull(result);
    assertEquals(documento, result.getDocumento());
  }

  @Test
  @Order(12)
  @DisplayName(value = "Teste cenario - NotFound - método RetornaVendedor")
  void testRetornaVendedor_WhenVendedorDoesNotExist_ThrowsNotFoundException() {
    // Arrange
    String documento = "1234567890";
    when(vendedorRepository.findByDocumento(documento)).thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> vendedorService.retornaVendedor(documento));
  }

  @Test
  @Order(13)
  @DisplayName(value = "Teste cenario - Sucess - método Cadastra")
  void testCadastra(){
    Filial filial = FilialMock.getFilialMock();
    Vendedor vendedor = VendedorMock.getVendedorMock_withFilial(filial);

    CadastroVendedorRequest cadastroVendedorRequest = CadastroVendedorRequest.builder()
              .email("yago.vcb@gmail.com")
              .dataNascimento(LocalDate.of(1995,11,15))
              .nome("Yago Castelo Branco")
              .documento("88717755204")
              .filialCnpj(filial.getCnpj())
              .tipoContratacao(TipoContracao.OUTSOURCING)
              .build();

    when(vendedorRepository.save(Mockito.<Vendedor>any())).thenReturn(vendedor);
    doNothing().when(vendedorRepository).updateMatricula(any(), any());
    doNothing().when(filialIntegrationService).atualizaFilialVendedor(Mockito.<Vendedor>any(), Mockito.<Filial>any());

    vendedorService.cadastra(cadastroVendedorRequest);
    verify(cadastroVendedorStatusService).atualizaCadastroStatus(any(), eq("Completo"), eq("Cadastro do vendedor concluído"));


  }

}
