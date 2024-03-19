package br.com.yagovcb.vendedorapi.application.service;

import br.com.yagovcb.vendedorapi.application.service.mapper.VendedorMapper;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendedorService {
    private final Logger log = LoggerFactory.getLogger(VendedorService.class);

    private final VendedorRepository vendedorRepository;
    private final VendedorMapper vendedorMapper;


    public ResponseEntity<List<VendedorResponse>> findAll() {
        return null;
    }

    public ResponseEntity<VendedorResponse> findById(Long id) {
        return null;
    }

    public ResponseEntity<VendedorResponse> cadastra(CadastroVendedorRequest cadastroVendedorRequest) {
        return null;
    }

    public ResponseEntity<HttpStatus> atualizaVendedor(Long id, AtualizaVendedorRequest atualizaVendedorRequest) {
        return null;
    }

    public ResponseEntity<HttpStatus> deleta(Long id) {
        return null;
    }
}
