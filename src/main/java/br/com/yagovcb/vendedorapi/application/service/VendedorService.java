package br.com.yagovcb.vendedorapi.application.service;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.IntegrationException;
import br.com.yagovcb.vendedorapi.infrastructure.integration.response.FilialResponse;
import br.com.yagovcb.vendedorapi.infrastructure.integration.service.FilialIntegrationService;
import br.com.yagovcb.vendedorapi.utils.VendedorUtils;
import br.com.yagovcb.vendedorapi.application.exceptions.NotFoundException;
import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import br.com.yagovcb.vendedorapi.domain.repository.VendedorRepository;
import br.com.yagovcb.vendedorapi.infrastructure.request.AtualizaVendedorRequest;
import br.com.yagovcb.vendedorapi.infrastructure.request.CadastroVendedorRequest;
import br.com.yagovcb.vendedorapi.infrastructure.response.VendedorResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendedorService {
    private final Logger log = LoggerFactory.getLogger(VendedorService.class);

    private final VendedorRepository vendedorRepository;
    private final FilialIntegrationService filialIntegrationService;

    public ResponseEntity<List<VendedorResponse>> findAll() {
        List<Vendedor> vendedorList = vendedorRepository.findAll();
        if (vendedorList.isEmpty()){
            throw new NotFoundException("Não foi encontrado nenhum vendedor na base.");
        }
        log.info("VendedorService :: Uma lista de vendedores foi encontrada na base.");

        List<VendedorResponse> response = new ArrayList<>();
        vendedorList.forEach(vendedor -> {
            FilialResponse filialResponse = retornaFilialResponse(vendedor.getFilial().getId());
            response.add(VendedorUtils.montaVendedorResponse(vendedor, filialResponse.getNome()));
        });
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<VendedorResponse> findById(Long id) {
        Vendedor vendedor = retornaVendedor(id);
        FilialResponse filialResponse = retornaFilialResponse(vendedor.getFilial().getId());
        return ResponseEntity.ok(VendedorUtils.montaVendedorResponse(vendedor, filialResponse.getNome()));
    }

    public ResponseEntity<VendedorResponse> cadastra(CadastroVendedorRequest cadastroVendedorRequest) {
        log.info("VendedorService :: Iniciando criação do vendedor na base...");
        log.info("VendedorService :: Iniciando validação do email...");
        VendedorUtils.validaEmail(cadastroVendedorRequest.getEmail());
        log.info("VendedorService :: Email validado, seguindo fluxo da API...");
        try {
            Vendedor vendedor = VendedorUtils.montaVendedorDefault(cadastroVendedorRequest);
            log.info("VendedorService :: Vendedor criado, iniciando geração da matricula");
            vendedorRepository.save(vendedor);
            log.info("VendedorService :: Persistencia inicial do objeto...");
            VendedorUtils.geraMatriculaVendedor(vendedor);
            log.info("VendedorService :: Matricula gerada, atualizando objeto...");
            vendedorRepository.updateMatricula(vendedor.getId(), vendedor.getMatricula());
            log.info("VendedorService :: Fluxo finalizado!");
            FilialResponse filialResponse = retornaFilialResponse(vendedor.getFilial().getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(VendedorUtils.montaVendedorResponse(vendedor, filialResponse.getNome()));
        } catch (IntegrationException e) {
            throw new IntegrationException(APIExceptionCode.UNKNOWN, e.getMessage());
        }
    }

    public ResponseEntity<HttpStatus> atualizaVendedor(Long id, AtualizaVendedorRequest atualizaVendedorRequest) {
        log.info("VendedorService :: Iniciando validação do email...");
        VendedorUtils.validaEmail(atualizaVendedorRequest.getEmail());
        log.info("VendedorService :: Email validado, seguindo fluxo da API...");
        Vendedor vendedor = retornaVendedor(id);
        try {
            log.info("VendedorService :: Iniciando atualização do objeto...");
            vendedorRepository.save(VendedorUtils.atualizaVendedor(vendedor, atualizaVendedorRequest));
            log.info("VendedorService :: Vendedor atualizado...");
            return ResponseEntity.noContent().build();
        } catch (IntegrationException e) {
            throw new IntegrationException(APIExceptionCode.UNKNOWN, e.getMessage());
        }
    }

    public ResponseEntity<HttpStatus> deleta(Long id) {
        Vendedor vendedor = retornaVendedor(id);
        log.info("VendedorService :: Iniciando a deleção do objeto...");
        try {
            vendedorRepository.delete(vendedor);
            log.info("VendedorService :: Vendedor deletado com sucesso!");
            return ResponseEntity.noContent().build();
        } catch (IntegrationException e) {
            throw new IntegrationException(APIExceptionCode.UNKNOWN, e.getMessage());
        }
    }

    private FilialResponse retornaFilialResponse(Long id){
        ResponseEntity<FilialResponse> responseEntity = filialIntegrationService.findById(id);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null){
            log.info("VendedorService :: Retornando filial para o fluxo...");
            return responseEntity.getBody();
        } else {
            throw new NotFoundException("Filial não encontrada na base...");
        }
    }

    private Vendedor retornaVendedor(Long id){
        Optional<Vendedor> optionalVendedor = vendedorRepository.findById(id);
        if (optionalVendedor.isEmpty()){
            throw new NotFoundException("Vendedor não encontrada na base...");
        }
        log.info("VendedorService :: Vendedor encontrado!");
        return optionalVendedor.get();
    }
}
