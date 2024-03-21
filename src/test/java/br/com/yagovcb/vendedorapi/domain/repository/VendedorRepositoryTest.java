package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.mock.FilialMock;
import br.com.yagovcb.vendedorapi.domain.mock.VendedorMock;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DisplayName("Teste da classe de repository VendedorRepository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class VendedorRepositoryTest {
    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    public FilialRepository filialRepository;

    @BeforeEach
    void setup(){
        Filial filial = FilialMock.getFilialMock();
        filial.setId(null);
        filialRepository.save(filial);
        vendedorRepository.save(VendedorMock.getVendedorMock_withFilial(filial));
    }


    @Test
    @Order(1)
    @DisplayName("Teste do método findByDocumento do repository")
    void findByUsernameTest(){
        Filial filial = FilialMock.getFilialMock();
        var documento = VendedorMock.getVendedorMock_withFilial(filial).getDocumento();
        Optional<Vendedor> optionalVendedor = vendedorRepository.findByDocumento(documento);
        assertTrue(optionalVendedor.isPresent());
        assertNotNull(optionalVendedor.get());
        var vendedor = optionalVendedor.get();
        assertEquals(documento, vendedor.getDocumento());

    }

    @Test
    @Order(2)
    @DisplayName("Teste do método existsByDocumento do repository")
    void existsByDocumentoTest(){
        boolean existsedByDocumento = vendedorRepository.existsByDocumento("88717755204");
        assertTrue(existsedByDocumento);

    }

    @Test
    @Order(3)
    @DisplayName("Teste do método updateMatricula do repository")
    void updateMatriculaTest(){
        Filial filial = FilialMock.getFilialMock();
        var documento = VendedorMock.getVendedorMock_withFilial(filial).getDocumento();
        Optional<Vendedor> optionalVendedor = vendedorRepository.findByDocumento(documento);
        assertTrue(optionalVendedor.isPresent());
        assertNotNull(optionalVendedor.get());
        var vendedor = optionalVendedor.get();

        vendedor.setMatricula("Atualizacao");
        vendedorRepository.updateMatricula(vendedor.getId(), vendedor.getMatricula());

        Optional<Vendedor> optionalVendedorAtualizado = vendedorRepository.findByDocumento(documento);
        assertTrue(optionalVendedorAtualizado.isPresent());
        assertNotNull(optionalVendedorAtualizado.get());
        var vendedorAtualizado = optionalVendedorAtualizado.get();
        assertEquals("Atualizacao", vendedorAtualizado.getMatricula());

    }
}
