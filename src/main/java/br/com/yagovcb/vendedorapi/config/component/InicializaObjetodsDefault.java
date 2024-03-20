package br.com.yagovcb.vendedorapi.config.component;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.IntegrationException;
import br.com.yagovcb.vendedorapi.application.service.VendedorService;
import br.com.yagovcb.vendedorapi.domain.enums.Role;
import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.model.Usuario;
import br.com.yagovcb.vendedorapi.domain.repository.FilialRepository;
import br.com.yagovcb.vendedorapi.domain.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InicializaObjetodsDefault {
    private final Logger log = LoggerFactory.getLogger(InicializaObjetodsDefault.class);

    private final UsuarioRepository usuarioRepository;
    private final FilialRepository filialRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${usuario.username}")
    private String usuario;
    @Value("${usuario.password}")
    private String password;

    @PostConstruct
    public void init(){
        log.info("Iniciando a criação de objetos default");
        Usuario usuarioDefault = Usuario.builder()
                .username(this.usuario)
                .senha(passwordEncoder.encode(this.password))
                .dataCadastro(LocalDate.now())
                .ativo(Boolean.TRUE)
                .roles(Set.of(Role.ROLE_ADMIN))
                .accountNonExpired(Boolean.TRUE)
                .accountNonLocked(Boolean.TRUE)
                .credentialsNonExpired(Boolean.TRUE)
                .build();
        try {
            log.info("Iniciando a persistencia do usuario default");
            usuarioRepository.save(usuarioDefault);
            log.info("Usuario default cadastrado...");
        } catch (IntegrationException e) {
            throw new IntegrationException(APIExceptionCode.UNKNOWN, e.getMessage());
        }
        Filial filial1 = Filial.builder()
                .nome("Empresa de teste 1")
                .cnpj("83317843000143")
                .cidade("Belém")
                .uf("PA")
                .tipo(TipoFilial.DEPOSITO)
                .ativo(Boolean.TRUE)
                .dataCadastro(LocalDate.now())
                .ultimaAtualizacao(LocalDate.now())
                .vendedores(new HashSet<>())
                .build();
        Filial filial2 = Filial.builder()
                .nome("Empresa de teste 2")
                .cnpj("54761418000140")
                .cidade("Rio de Janeiro")
                .uf("RJ")
                .tipo(TipoFilial.LOJA)
                .ativo(Boolean.TRUE)
                .dataCadastro(LocalDate.now())
                .ultimaAtualizacao(LocalDate.now())
                .vendedores(new HashSet<>())
                .build();

        Filial filial3 = Filial.builder()
                .nome("Empresa de teste 3")
                .cnpj("32094992000150")
                .cidade("São Paulo")
                .uf("SP")
                .tipo(TipoFilial.SHOPPING)
                .ativo(Boolean.TRUE)
                .dataCadastro(LocalDate.now())
                .ultimaAtualizacao(LocalDate.now())
                .vendedores(new HashSet<>())
                .build();
        try {
            log.info("Iniciando a persistencia das filiais default");
            filialRepository.saveAll(List.of(filial1, filial2, filial3));
        } catch (IntegrationException e) {
            throw new IntegrationException(APIExceptionCode.UNKNOWN, e.getMessage());
        }

        log.info("\nObjetos default 100% cadastrados!\n");
    }
}
