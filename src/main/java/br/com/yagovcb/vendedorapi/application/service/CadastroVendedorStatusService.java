package br.com.yagovcb.vendedorapi.application.service;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.IntegrationException;
import br.com.yagovcb.vendedorapi.application.exceptions.NotFoundException;
import br.com.yagovcb.vendedorapi.domain.model.CadastroVendedorStatus;
import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import br.com.yagovcb.vendedorapi.domain.repository.CadastroVendedorStatusRepository;
import br.com.yagovcb.vendedorapi.domain.repository.VendedorRepository;
import br.com.yagovcb.vendedorapi.infrastructure.response.CadastroStatusResponse;
import br.com.yagovcb.vendedorapi.utils.VendedorUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroVendedorStatusService {
    private final Logger log = LoggerFactory.getLogger(CadastroVendedorStatusService.class);

    private final CadastroVendedorStatusRepository cadastroVendedorStatusRepository;
    private final VendedorRepository vendedorRepository;

    public ResponseEntity<CadastroStatusResponse> buscaVendedorStatus(String documento) {
        Optional<Vendedor> vendedorOptional = vendedorRepository.findByDocumento(documento);
        if (vendedorOptional.isEmpty()) {
            throw new NotFoundException("Vendedor não encontrado na base...");
        }
        log.info("CadastroStatusService :: Vendedor encontrado!");
        Optional<CadastroVendedorStatus> cadastroVendedorStatusOptional = cadastroVendedorStatusRepository.findByDocumentoVendedor(documento);
        if (cadastroVendedorStatusOptional.isEmpty()) {
            throw new NotFoundException("Status do cadastro não encontrado na base...");
        }
        log.info("CadastroStatusService :: Status do cadastro encontrado!");
        var statusCadastro = cadastroVendedorStatusOptional.get();
        log.info("CadastroStatusService :: Retornando informação do status de cadastro...");
        return ResponseEntity.ok(CadastroStatusResponse.builder()
                        .status(statusCadastro.getStatus())
                        .mensagem(statusCadastro.getMensagem())
                        .vendedor(VendedorUtils.montaVendedorResponse(vendedorOptional.get(), null))
                .build());
    }

    public Long cadastraStatusNovo(String documento){
        log.info("CadastroStatusService :: Cadastrando status de persistencia assincrona");
        try {
            CadastroVendedorStatus cadastroVendedorStatus = CadastroVendedorStatus.builder()
                    .status("Pendente")
                    .documentoVendedor(documento)
                    .mensagem("Iniciando o cadastro do vendedor")
                    .build();
            cadastroVendedorStatusRepository.save(cadastroVendedorStatus);
            log.info("CadastroStatusService :: Cadastro do status realidado...");
            return cadastroVendedorStatus.getId();
        } catch (IntegrationException e) {
            throw new IntegrationException(APIExceptionCode.UNKNOWN, e.getMessage());
        }
    }

    public void atualizaCadastroStatus(Long id, String status, String mensagem){
        log.info("CadastroStatusService :: Atualizando status do cadastro do vendedor...");
        Optional<CadastroVendedorStatus> cadastroVendedorStatusOptional = cadastroVendedorStatusRepository.findById(id);
        if (cadastroVendedorStatusOptional.isEmpty()) {
            throw new NotFoundException("Status do cadastro não encontrado na base...");
        }
        log.info("CadastroStatusService :: Status do cadastro encontrado, atualizando...");
        var statusCadastro = cadastroVendedorStatusOptional.get();
        statusCadastro.setStatus(status);
        statusCadastro.setMensagem(mensagem);
        try {
            cadastroVendedorStatusRepository.save(statusCadastro);
            log.info("CadastroStatusService :: Cadastro do status atualizado com sucesso!");
        } catch (IntegrationException e) {
            throw new IntegrationException(APIExceptionCode.UNKNOWN, e.getMessage());
        }
    }

}
