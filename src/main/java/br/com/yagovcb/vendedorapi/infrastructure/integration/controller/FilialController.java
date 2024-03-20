package br.com.yagovcb.vendedorapi.infrastructure.integration.controller;

import br.com.yagovcb.vendedorapi.application.controller.VendedorController;
import br.com.yagovcb.vendedorapi.application.service.VendedorService;
import br.com.yagovcb.vendedorapi.infrastructure.integration.response.FilialResponse;
import br.com.yagovcb.vendedorapi.infrastructure.integration.service.FilialIntegrationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filial")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class FilialController {

    private final Logger log = LoggerFactory.getLogger(FilialController.class);
    private final FilialIntegrationService filialIntegrationService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FilialResponse>> buscaTodos() {
        log.info("FilialController :: Iniciando a API para buscar todos os objetos cadastrados...");
        return filialIntegrationService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilialResponse> buscaPorId(@PathVariable("id") Long id) {
        log.info("FilialController :: Iniciando a API para buscar objeto por id...");
        return filialIntegrationService.findById(id);
    }
}
