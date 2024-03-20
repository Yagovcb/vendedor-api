package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.model.CadastroVendedorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CadastroVendedorStatusRepository extends JpaRepository<CadastroVendedorStatus, Long> {

    Optional<CadastroVendedorStatus> findByDocumentoVendedor(String documentoVendedor);
}