package br.com.yagovcb.vendedorapi.domain.model;

import br.com.yagovcb.vendedorapi.domain.enums.Role;
import br.com.yagovcb.vendedorapi.domain.mock.UsuarioMock;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testGetAuthorities() {
        assertTrue((new Usuario()).getAuthorities().isEmpty());
    }

    @Test
    void testGetAuthoritiesWithUsuario() {
        HashSet<Role> roleSet = new HashSet<>();
        roleSet.add(Role.ROLE_USER);
        Usuario usuario = new Usuario(123L, "janedoe", "iloveyou", LocalDate.now(), true, roleSet,
                true, true, true);
        assertEquals(1, usuario.getAuthorities().size());
    }

    @Test
    void testGetAuthoritiesWithCustomUsuario() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.ROLE_ADMIN);
        roleSet.add(Role.ROLE_USER);
        Usuario Usuario = UsuarioMock.getUsuarioWithFullRoleMock();
        assertEquals(Usuario.getRoles(), roleSet);
    }

    @Test
    void testConstructor() {
        Usuario actualUsuario = UsuarioMock.getUsuarioWithFullRoleMock();
        assertTrue(actualUsuario.isAccountNonExpired());
        assertTrue(actualUsuario.isAccountNonLocked());
        assertTrue(actualUsuario.isCredentialsNonExpired());
        assertTrue(actualUsuario.isEnabled());
    }

    @Test
    void testEquals() {
        Usuario Usuario = UsuarioMock.getUser_RoleMock();

        Usuario otherUsuario = UsuarioMock.getUser_withoutRoleMock();
        assertNotEquals(otherUsuario, Usuario);
        int expectedHashCodeResult = Usuario.hashCode();
        assertEquals(expectedHashCodeResult, otherUsuario.hashCode());
    }

    @Test
    void testGetterSetter() {
        Usuario usuario = UsuarioMock.getUsuarioWithFullRoleMock();
        assertNotNull(usuario.getId());
        assertEquals("janedoe", usuario.getUsername());
        assertEquals("Senha", usuario.getPassword());
        assertTrue(usuario.isEnabled());
        assertEquals(LocalDate.now(), usuario.getDataCadastro());
        assertFalse(usuario.getRoles().isEmpty());
        assertTrue(usuario.getRoles().contains(Role.ROLE_USER));
        assertTrue(usuario.isAccountNonExpired());
        assertTrue(usuario.isAccountNonLocked());
        assertTrue(usuario.isCredentialsNonExpired());
    }

    @Test
    void testGetterSetter_manual() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("janedoe");
        usuario.setSenha("Senha");
        usuario.setAtivo(Boolean.TRUE);
        usuario.setDataCadastro(LocalDate.now());
        usuario.setRoles(Set.of(Role.ROLE_USER, Role.ROLE_ADMIN));
        usuario.setCredentialsNonExpired(Boolean.TRUE);
        usuario.setAccountNonExpired(Boolean.TRUE);
        usuario.setAccountNonLocked(Boolean.TRUE);
        assertNotNull(usuario.getId());
        assertEquals("janedoe", usuario.getUsername());
        assertEquals("Senha", usuario.getPassword());
        assertTrue(usuario.isEnabled());
        assertEquals(LocalDate.now(), usuario.getDataCadastro());
        assertFalse(usuario.getRoles().isEmpty());
        assertTrue(usuario.getRoles().contains(Role.ROLE_USER));
        assertTrue(usuario.isAccountNonExpired());
        assertTrue(usuario.isAccountNonLocked());
        assertTrue(usuario.isCredentialsNonExpired());
    }
}