package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Long> {
    Optional<Filial> findByCnpj(String cnpj);

    @Query(value = "select f from Filial f join f.vendedores v where f.cnpj = :cnpj")
    Filial findFilialComVendedores(@Param("cnpj") String cnpj);
}