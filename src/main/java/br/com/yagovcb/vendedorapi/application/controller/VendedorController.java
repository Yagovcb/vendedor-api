package br.com.yagovcb.vendedorapi.application.controller;

import br.com.yagovcb.vendedorapi.application.service.CadastroVendedorStatusService;
import br.com.yagovcb.vendedorapi.application.service.VendedorService;
import br.com.yagovcb.vendedorapi.infrastructure.request.AtualizaVendedorRequest;
import br.com.yagovcb.vendedorapi.infrastructure.request.CadastroVendedorRequest;
import br.com.yagovcb.vendedorapi.infrastructure.response.VendedorResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendedor")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class VendedorController {
    private final Logger log = LoggerFactory.getLogger(VendedorController.class);
    private final VendedorService vendedorService;
    private CadastroVendedorStatusService cadastroVendedorStatusService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VendedorResponse>> buscaTodos() {
        log.info("PessoaController :: Iniciando a API para buscar todos os objetos cadastrados...");
        return vendedorService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VendedorResponse> buscaPorId(@PathVariable("id") Long id) {
        log.info("PessoaController :: Iniciando a API para buscar objeto por id...");
        return vendedorService.findById(id);
    }

    @GetMapping(value = "/{documento}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscaVendedorStatus(@PathVariable("documento") String documento) {
        log.info("PessoaController :: Iniciando a API para buscar objeto por documento...");
        return cadastroVendedorStatusService.buscaVendedorStatus(documento);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cadastra(@RequestBody CadastroVendedorRequest cadastroVendedorRequest) {
        log.info("PessoaController :: Iniciando a API para persistir objeto...");
        vendedorService.cadastra(cadastroVendedorRequest);
        return ResponseEntity.accepted().body("Cadastro em processamento");
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> atualizaVendedor(@PathVariable("id") Long id, @RequestBody AtualizaVendedorRequest atualizaVendedorRequest) {
        log.info("PessoaController :: Iniciando a API para atualizar a pessoa...");
        return vendedorService.atualizaVendedor(id, atualizaVendedorRequest);
    }


    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleta(@PathVariable("id") Long id) {
        log.info("PessoaController :: Iniciando a API para deletar motorista da base...");
        return vendedorService.deleta(id);
    }
}
