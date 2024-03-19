package br.com.yagovcb.vendedorapi.domain.repository;

import br.com.yagovcb.vendedorapi.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findAllByEnabledTrue();

    boolean existsUsuarioByUsername(String username);
}
