package br.com.yagovcb.vendedorapi.application.controller;

import br.com.yagovcb.vendedorapi.VendedorApiApplication;
import br.com.yagovcb.vendedorapi.application.service.CadastroVendedorStatusService;
import br.com.yagovcb.vendedorapi.application.service.VendedorService;
import br.com.yagovcb.vendedorapi.domain.enums.TipoContracao;
import br.com.yagovcb.vendedorapi.domain.mock.FilialMock;
import br.com.yagovcb.vendedorapi.domain.mock.VendedorMock;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import br.com.yagovcb.vendedorapi.infrastructure.request.CadastroVendedorRequest;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("VendedorControllerIT - Teste de integração da API de Vendedor")
class VendedorControllerIT {

    private static final String URI_BASE = "/api/v1/vendedor/";

    @Autowired
    private MockMvc restVendedorMockMvc;

    @Test
    void buscaTodosTest() throws Exception {

        restVendedorMockMvc.perform(get(URI_BASE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // .andExpect(jsonPath("$.algumCampo").value("valorEsperado")); // Se necessário, para verificar o conteúdo
    }

    @Test
    void buscaPorIdTest() throws Exception {

        restVendedorMockMvc.perform(get(URI_BASE + "{documento}", "123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // .andExpect(jsonPath("$.algumCampo").value("valorEsperado"));
    }
}
