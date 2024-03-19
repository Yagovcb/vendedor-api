package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Long> {
}