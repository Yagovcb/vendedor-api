package br.com.yagovcb.vendedorapi.domain.mock;

import br.com.yagovcb.vendedorapi.domain.enums.Role;
import br.com.yagovcb.vendedorapi.domain.model.Usuario;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UsuarioMock {

    public static Usuario getUser_RoleMock(){
        return Usuario.builder()
                .id(1L)
                .username("janedoe")
                .senha("Senha")
                .ativo(true)
                .dataCadastro(LocalDate.now())
                .roles(Set.of(Role.ROLE_USER))
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonLocked(true)
                .build();
    }

    public static Usuario getUser_withoutRoleMock(){
        return Usuario.builder()
                .id(2L)
                .username("teste_user")
                .senha("testeUser123")
                .ativo(true)
                .dataCadastro(LocalDate.now())
                .roles(new HashSet<>())
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonLocked(true)
                .build();
    }


}
