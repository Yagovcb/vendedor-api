package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
}