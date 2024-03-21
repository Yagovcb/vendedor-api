package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Optional<Vendedor> findByDocumento(String documento);

    boolean existsByDocumento(String documento);

    @Modifying
    @Transactional
    @Query(value = "update Vendedor v set v.matricula = :matricula where v.id = :id")
    void updateMatricula(@Param("id") Long id, @Param("matricula") String matricula);
}