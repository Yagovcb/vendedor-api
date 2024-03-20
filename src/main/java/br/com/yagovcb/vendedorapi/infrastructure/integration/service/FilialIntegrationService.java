package br.com.yagovcb.vendedorapi.infrastructure.integration.service;

import br.com.yagovcb.vendedorapi.infrastructure.integration.utils.FilialUtils;
import br.com.yagovcb.vendedorapi.application.exceptions.NotFoundException;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.repository.FilialRepository;
import br.com.yagovcb.vendedorapi.infrastructure.integration.response.FilialResponse;
import br.com.yagovcb.vendedorapi.infrastructure.response.VendedorResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilialIntegrationService {

    private final Logger log = LoggerFactory.getLogger(FilialIntegrationService.class);
    private final FilialRepository filialRepository;

    public ResponseEntity<FilialResponse> findById(Long id) {
        Optional<Filial> optionalFilial = filialRepository.findById(id);
        if (optionalFilial.isEmpty()){
            throw new NotFoundException("Filial não encontrada na base.");
        }
        log.info("FilialIntegrationService :: Filial encontrada...");
        var filial = optionalFilial.get();
        return ResponseEntity.ok(FilialUtils.montaFilialResponse(filial));
    }

    public ResponseEntity<List<FilialResponse>> findAll() {
        List<Filial> filialList = filialRepository.findAll();
        if (filialList.isEmpty()){
            throw new NotFoundException("Não foi encontrado nenhuma filial na base.");
        }
        log.info("FilialIntegrationService :: Uma lista de filiais foi encontrada na base.");
        List<FilialResponse> response = new ArrayList<>();
        filialList.forEach(filial -> response.add(FilialUtils.montaFilialResponse(filial)));
        return ResponseEntity.ok(response);
    }
}
