package br.com.yagovcb.vendedorapi.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.IntegrationException;
import br.com.yagovcb.vendedorapi.application.exceptions.NotFoundException;
import br.com.yagovcb.vendedorapi.domain.enums.TipoContracao;
import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;
import br.com.yagovcb.vendedorapi.domain.mock.CadastroVendedorStatusMock;
import br.com.yagovcb.vendedorapi.domain.mock.FilialMock;
import br.com.yagovcb.vendedorapi.domain.mock.VendedorMock;
import br.com.yagovcb.vendedorapi.domain.model.CadastroVendedorStatus;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import br.com.yagovcb.vendedorapi.domain.repository.CadastroVendedorStatusRepository;
import br.com.yagovcb.vendedorapi.domain.repository.VendedorRepository;
import br.com.yagovcb.vendedorapi.infrastructure.response.CadastroStatusResponse;
import br.com.yagovcb.vendedorapi.infrastructure.response.VendedorResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CadastroVendedorStatusService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("CadastroVendedorStatusService - Classe de teste unitario")
class CadastroVendedorStatusServiceTest {
    @MockBean
    private CadastroVendedorStatusRepository cadastroVendedorStatusRepository;

    @Autowired
    private CadastroVendedorStatusService cadastroVendedorStatusService;

    @MockBean
    private VendedorRepository vendedorRepository;

    /**
     * Method under test:
     * {@link CadastroVendedorStatusService#buscaVendedorStatus(String)}
     */
    @Test
    @Order(1)
    @DisplayName(value = "Teste cenario - Parcial - método buscaVendedorStatus")
    void testBuscaVendedorStatus() {
        // Arrange
        CadastroVendedorStatus cadastroVendedorStatus = CadastroVendedorStatusMock.getCadastroVendedorStatusMock();
        Optional<CadastroVendedorStatus> ofResult = Optional.of(cadastroVendedorStatus);
        when(cadastroVendedorStatusRepository.findByDocumentoVendedor(any())).thenReturn(ofResult);

        Filial filial = FilialMock.getFilialMock();

        Vendedor vendedor = VendedorMock.getVendedorMock_withFilial(filial);
        Optional<Vendedor> ofResult2 = Optional.of(vendedor);
        when(vendedorRepository.findByDocumento(any())).thenReturn(ofResult2);

        // Act
        ResponseEntity<CadastroStatusResponse> actualBuscaVendedorStatusResult = cadastroVendedorStatusService
                .buscaVendedorStatus(vendedor.getDocumento());

        // Assert
        verify(cadastroVendedorStatusRepository).findByDocumentoVendedor(vendedor.getDocumento());
        verify(vendedorRepository).findByDocumento(vendedor.getDocumento());
        CadastroStatusResponse body = actualBuscaVendedorStatusResult.getBody();
        VendedorResponse vendedorResponse = body.getVendedor();
        assertEquals("1970-01-01", vendedorResponse.getDataNascimento().toString());
        assertEquals("0000001-OUT", vendedorResponse.getMatricula());
        assertEquals("Mensagem", body.getMensagem());
        assertEquals("Vendedor Teste", vendedorResponse.getNome());
        assertEquals("Status", body.getStatus());
        assertEquals("88717755204", vendedorResponse.getDocumento());
        assertNull(vendedorResponse.getEmail());
        assertNull(vendedorResponse.getNomeFilial());
        assertEquals(200, actualBuscaVendedorStatusResult.getStatusCode().value());
        assertTrue(actualBuscaVendedorStatusResult.hasBody());
        assertTrue(actualBuscaVendedorStatusResult.getHeaders().isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName(value = "Teste cenario - NotFound Vendedor - método buscaVendedorStatus")
    void testBuscaVendedorStatus_notFoundVendedor() {
        var documentoFalso = "12345678900";
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> cadastroVendedorStatusService.buscaVendedorStatus(documentoFalso));
        assertNotNull(notFoundException);
        assertEquals("Vendedor não encontrado na base...", notFoundException.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName(value = "Teste cenario - NotFound Vendedor CadastroStatus - método buscaVendedorStatus")
    void testBuscaVendedorStatus_notFoundCadastroStatus() {
        Filial filial = FilialMock.getFilialMock();
        Vendedor vendedor = VendedorMock.getVendedorMock_withFilial(filial);
        Optional<Vendedor> ofResult2 = Optional.of(vendedor);
        when(vendedorRepository.findByDocumento(any())).thenReturn(ofResult2);

        var documentoFalso = "12345678900";

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> cadastroVendedorStatusService.buscaVendedorStatus(documentoFalso));
        assertNotNull(notFoundException);
        assertEquals("Status do cadastro não encontrado na base...", notFoundException.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName(value = "Teste cenario - Sucess - método CadastraStatusNovo")
    void testCadastraStatusNovo() {
        // Arrange
        CadastroVendedorStatus cadastroVendedorStatus = CadastroVendedorStatusMock.getCadastroVendedorStatusMock();
        when(cadastroVendedorStatusRepository.save(any())).thenReturn(cadastroVendedorStatus);

        // Act
        Long actualCadastraStatusNovoResult = cadastroVendedorStatusService.cadastraStatusNovo("alice.liddell@example.org");

        // Assert
        verify(cadastroVendedorStatusRepository).save(any());
        assertNull(actualCadastraStatusNovoResult);
    }

    @Test
    @Order(5)
    @DisplayName(value = "Teste cenario - IntegrationException - método CadastraStatusNovo")
    void testCadastraStatusNovo_integrationException() {
        // Arrange
        when(cadastroVendedorStatusRepository.save(Mockito.<CadastroVendedorStatus>any()))
                .thenThrow(new IntegrationException(APIExceptionCode.UNKNOWN, "An error occurred"));

        // Act and Assert
        assertThrows(IntegrationException.class,
                () -> cadastroVendedorStatusService.cadastraStatusNovo("alice.liddell@example.org"));
        verify(cadastroVendedorStatusRepository).save(Mockito.<CadastroVendedorStatus>any());
    }

    @Test
    @Order(6)
    @DisplayName(value = "Teste cenario - Sucess - método AtualizaCadastroStatus")
    void testAtualizaCadastroStatus() {
        // Arrange
        CadastroVendedorStatus cadastroVendedorStatus = CadastroVendedorStatusMock.getCadastroVendedorStatusMock();
        Optional<CadastroVendedorStatus> ofResult = Optional.of(cadastroVendedorStatus);

        CadastroVendedorStatus cadastroVendedorStatus2 = CadastroVendedorStatusMock.getCadastroVendedorStatusMockSegundoTeste();
        when(cadastroVendedorStatusRepository.save(any())).thenReturn(cadastroVendedorStatus2);
        when(cadastroVendedorStatusRepository.findById(any())).thenReturn(ofResult);

        // Act
        cadastroVendedorStatusService.atualizaCadastroStatus(1L, "Concluído", "teste Concluído");

        // Assert
        verify(cadastroVendedorStatusRepository).findById(any());
        verify(cadastroVendedorStatusRepository).save(any());
    }

    @Test
    @Order(7)
    @DisplayName(value = "Teste cenario - NotFound - método AtualizaCadastroStatus")
    void testAtualizaCadastroStatus_notFound() {
        // Act and Assert
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> cadastroVendedorStatusService.atualizaCadastroStatus(1L, "Status", "Mensagem"));
        assertNotNull(notFoundException);
        assertEquals("Status do cadastro não encontrado na base...", notFoundException.getMessage());
    }
    @Test
    @Order(8)
    @DisplayName(value = "Teste cenario - IntegrationException - método AtualizaCadastroStatus")
    void testAtualizaCadastroStatus_integrationException() {
        // Arrange
        CadastroVendedorStatus cadastroVendedorStatus = CadastroVendedorStatusMock.getCadastroVendedorStatusMock();
        Optional<CadastroVendedorStatus> ofResult = Optional.of(cadastroVendedorStatus);
        when(cadastroVendedorStatusRepository.save(Mockito.<CadastroVendedorStatus>any()))
                .thenThrow(new IntegrationException(APIExceptionCode.UNKNOWN, "An error occurred"));
        when(cadastroVendedorStatusRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IntegrationException.class,
                () -> cadastroVendedorStatusService.atualizaCadastroStatus(1L, "Status", "Mensagem"));
        verify(cadastroVendedorStatusRepository).findById(Mockito.<Long>any());
        verify(cadastroVendedorStatusRepository).save(Mockito.<CadastroVendedorStatus>any());
    }

}
