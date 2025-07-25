package br.com.yagovcb.vendedorapi.application.service;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.BusinessException;
import br.com.yagovcb.vendedorapi.application.exceptions.IntegrationException;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
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
import org.springframework.scheduling.annotation.Async;
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
    private final CadastroVendedorStatusService cadastroVendedorStatusService;

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

    public ResponseEntity<VendedorResponse> findByDocumento(String documento) {
        Vendedor vendedor = retornaVendedor(documento);
        FilialResponse filialResponse = retornaFilialResponse(vendedor.getFilial().getId());
        return ResponseEntity.ok(VendedorUtils.montaVendedorResponse(vendedor, filialResponse.getNome()));
    }

    @Async
    public void cadastra(CadastroVendedorRequest cadastroVendedorRequest) {
        Long id = cadastroVendedorStatusService.cadastraStatusNovo(cadastroVendedorRequest.getDocumento());
        log.info("VendedorService :: Iniciando criação do vendedor na base...");
        log.info("VendedorService :: Iniciando validação do email...");
        try {
            VendedorUtils.validaEmail(cadastroVendedorRequest.getEmail());
            log.info("VendedorService :: Email validado, seguindo fluxo da API...");
            Filial filial = filialIntegrationService.findByCnpj(cadastroVendedorRequest.getFilialCnpj());
            Vendedor vendedor = VendedorUtils.montaVendedorDefault(cadastroVendedorRequest,filial);
            log.info("VendedorService :: Vendedor criado, iniciando geração da matricula");
            vendedorRepository.save(vendedor);
            log.info("VendedorService :: Persistencia inicial do objeto...");
            VendedorUtils.geraMatriculaVendedor(vendedor);
            log.info("VendedorService :: Matricula gerada, atualizando objeto...");
            vendedorRepository.updateMatricula(vendedor.getId(), vendedor.getMatricula());
            filialIntegrationService.atualizaFilialVendedor(vendedor, filial);
            log.info("VendedorService :: Fluxo finalizado!");
            cadastroVendedorStatusService.atualizaCadastroStatus(id, "Completo", "Cadastro do vendedor concluído");
        } catch (IntegrationException e) {
            cadastroVendedorStatusService.atualizaCadastroStatus(id, "Error", e.getMessage());
            throw new IntegrationException(APIExceptionCode.UNKNOWN, e.getMessage());
        }
    }

    public ResponseEntity<HttpStatus> atualizaVendedor(String documento, AtualizaVendedorRequest atualizaVendedorRequest) {
        if (Boolean.TRUE.equals(validaVendedorJaExistente(documento))){
            throw new NotFoundException("Vendedor, com documento passado, não existe na base");
        }

        log.info("VendedorService :: Iniciando validação do email...");
        VendedorUtils.validaEmail(atualizaVendedorRequest.getEmail());
        log.info("VendedorService :: Email validado, seguindo fluxo da API...");
        Vendedor vendedor = retornaVendedor(documento);
        try {
            log.info("VendedorService :: Iniciando atualização do objeto...");
            vendedorRepository.save(VendedorUtils.atualizaVendedor(vendedor, atualizaVendedorRequest));
            log.info("VendedorService :: Vendedor atualizado...");
            return ResponseEntity.noContent().build();
        } catch (IntegrationException e) {
            throw new IntegrationException(APIExceptionCode.UNKNOWN, e.getMessage());
        }
    }

    public ResponseEntity<HttpStatus> deleta(String documento) {
        if (Boolean.TRUE.equals(validaVendedorJaExistente(documento))){
            throw new NotFoundException("Vendedor, com documento passado, não existe na base");
        }
        Vendedor vendedor = retornaVendedor(documento);
        log.info("VendedorService :: Iniciando a deleção do objeto...");
        try {
            vendedorRepository.delete(vendedor);
            log.info("VendedorService :: Vendedor deletado com sucesso!");
            return ResponseEntity.noContent().build();
        } catch (IntegrationException e) {
            throw new IntegrationException(APIExceptionCode.UNKNOWN, e.getMessage());
        }
    }

    public Boolean validaVendedorJaExistente(String documento){
        return vendedorRepository.existsByDocumento(documento);
    }

    public FilialResponse retornaFilialResponse(Long id){
        ResponseEntity<FilialResponse> responseEntity = filialIntegrationService.findById(id);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null){
            log.info("VendedorService :: Retornando filial para o fluxo...");
            return responseEntity.getBody();
        } else {
            throw new NotFoundException("Filial não encontrada na base...");
        }
    }

    public Vendedor retornaVendedor(String documento){
        if (!VendedorUtils.validaDocumentoVendedor(documento)) {
            throw new BusinessException(APIExceptionCode.CONSTRAINT_VALIDATION, "Documento não segue o padrão definido!");
        }
        Optional<Vendedor> optionalVendedor = vendedorRepository.findByDocumento(documento);
        if (optionalVendedor.isEmpty()){
            throw new NotFoundException("Vendedor não encontrada na base...");
        }
        log.info("VendedorService :: Vendedor encontrado!");
        return optionalVendedor.get();
    }
}
