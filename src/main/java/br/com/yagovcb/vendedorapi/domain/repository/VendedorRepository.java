package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {


    @Modifying
    @Transactional
    @Query(value = "update Vendedor v set v.matricula = :matricula where v.id = :id")
    void updateMatricula(Long id, String matricula);
}