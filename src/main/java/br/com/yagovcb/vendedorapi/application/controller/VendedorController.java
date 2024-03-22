package br.com.yagovcb.vendedorapi.application.controller;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.BusinessException;
import br.com.yagovcb.vendedorapi.application.exceptions.ConflictException;
import br.com.yagovcb.vendedorapi.application.service.CadastroVendedorStatusService;
import br.com.yagovcb.vendedorapi.application.service.VendedorService;
import br.com.yagovcb.vendedorapi.infrastructure.request.AtualizaVendedorRequest;
import br.com.yagovcb.vendedorapi.infrastructure.request.CadastroVendedorRequest;
import br.com.yagovcb.vendedorapi.infrastructure.response.CadastroStatusResponse;
import br.com.yagovcb.vendedorapi.infrastructure.response.VendedorResponse;
import br.com.yagovcb.vendedorapi.utils.VendedorUtils;
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

    @GetMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VendedorResponse> buscaPorId(@PathVariable("documento") String documento) {
        log.info("PessoaController :: Iniciando a API para buscar objeto por id...");
        return vendedorService.findByDocumento(documento);
    }

    @GetMapping(value = "/{documento}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CadastroStatusResponse> buscaVendedorStatus(@PathVariable("documento") String documento) {
        log.info("PessoaController :: Iniciando a API para buscar objeto por documento...");
        return cadastroVendedorStatusService.buscaVendedorStatus(documento);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cadastra(@RequestBody CadastroVendedorRequest cadastroVendedorRequest) {
        log.info("PessoaController :: Iniciando a API para persistir objeto...");
        if (Boolean.TRUE.equals(vendedorService.validaVendedorJaExistente(cadastroVendedorRequest.getDocumento()))){
            throw new ConflictException(APIExceptionCode.RESOURCE_ALREADY_EXISTS, "Vendedor já cadastrado na base...");
        } else if (!VendedorUtils.validaDocumentoVendedor(cadastroVendedorRequest.getDocumento())) {
            throw new BusinessException(APIExceptionCode.CONSTRAINT_VALIDATION, "Documento não segue o padrão definido!");
        } else {
            vendedorService.cadastra(cadastroVendedorRequest);
            return ResponseEntity.accepted().body("Cadastro em processamento");
        }
    }

    @PatchMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> atualizaVendedor(@PathVariable("documento") String documento, @RequestBody AtualizaVendedorRequest atualizaVendedorRequest) {
        log.info("PessoaController :: Iniciando a API para atualizar a pessoa...");
        return vendedorService.atualizaVendedor(documento, atualizaVendedorRequest);
    }


    @DeleteMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleta(@PathVariable("documento") String documento) {
        log.info("PessoaController :: Iniciando a API para deletar motorista da base...");
        return vendedorService.deleta(documento);
    }
}
