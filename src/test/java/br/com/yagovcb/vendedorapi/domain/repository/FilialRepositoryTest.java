package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;
import br.com.yagovcb.vendedorapi.domain.mock.FilialMock;
import br.com.yagovcb.vendedorapi.domain.mock.VendedorMock;
import br.com.yagovcb.vendedorapi.domain.model.Filial;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import br.com.yagovcb.vendedorapi.infrastructure.integration.utils.FilialUtils;
import br.com.yagovcb.vendedorapi.utils.Utils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DisplayName("Teste da classe de repository FilialRepository")
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class FilialRepositoryTest {

    @Autowired
    private FilialRepository filialRepository;

    @BeforeEach
    void setup(){
        Filial filial = FilialMock.getFilialMock();
        filial.setId(null);
        filialRepository.save(filial);
    }

    @Test
    @DisplayName("Teste do método findByCnpj do repository")
    void testFindByCnpj() {
        String cnpj = FilialMock.getFilialMock().getCnpj();
        Optional<Filial> filialOptional = filialRepository.findByCnpj(Utils.desformatarDocumento(cnpj));
        assertTrue(filialOptional.isPresent());
        assertNotNull(filialOptional.get());
        var filialSaved = filialOptional.get();
        assertEquals(cnpj, filialSaved.getCnpj());
    }
}
