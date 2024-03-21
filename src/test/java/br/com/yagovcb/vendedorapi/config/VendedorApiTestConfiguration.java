package br.com.yagovcb.vendedorapi.config;

import br.com.yagovcb.vendedorapi.application.service.AuthenticationServices;
import br.com.yagovcb.vendedorapi.application.service.CadastroVendedorStatusService;
import br.com.yagovcb.vendedorapi.application.service.JwtService;
import br.com.yagovcb.vendedorapi.application.service.VendedorService;
import br.com.yagovcb.vendedorapi.domain.enums.Role;
import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.model.Usuario;
import br.com.yagovcb.vendedorapi.domain.repository.*;
import br.com.yagovcb.vendedorapi.infrastructure.integration.service.FilialIntegrationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@TestConfiguration
public class VendedorApiTestConfiguration {

    @Autowired
    private CadastroVendedorStatusRepository cadastroVendedorStatusRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilialRepository filialRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public FilialIntegrationService filialIntegrationService(){
        return new FilialIntegrationService(filialRepository);
    }

    @Bean
    public VendedorService vendedorService(){
        return new VendedorService(vendedorRepository, filialIntegrationService(), cadastroVendedorStatusService());
    }

    @Bean
    public CadastroVendedorStatusService cadastroVendedorStatusService(){
        return new CadastroVendedorStatusService(cadastroVendedorStatusRepository, vendedorRepository);
    }

    @Bean
    public AuthenticationServices authenticationServices(){
       return new AuthenticationServices(authenticationManager, usuarioRepository, tokenRepository, jwtService);
    }

    @PostConstruct
    public void init() {
        Usuario usuarioDefault = Usuario.builder()
                .username("admTeste")
                .senha(passwordEncoder.encode("passwordTeste"))
                .dataCadastro(LocalDate.now())
                .ativo(Boolean.TRUE)
                .roles(Set.of(Role.ROLE_ADMIN))
                .accountNonExpired(Boolean.TRUE)
                .accountNonLocked(Boolean.TRUE)
                .credentialsNonExpired(Boolean.TRUE)
                .build();
        usuarioRepository.save(usuarioDefault);
        Filial filial1 = Filial.builder()
                .nome("Empresa de teste 1")
                .cnpj("83317843000143")
                .cidade("Belém")
                .uf("PA")
                .tipo(TipoFilial.DEPOSITO)
                .ativo(Boolean.TRUE)
                .dataCadastro(LocalDate.now())
                .ultimaAtualizacao(LocalDate.now())
                .vendedores(new ArrayList<>())
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
                .vendedores(new ArrayList<>())
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
                .vendedores(new ArrayList<>())
                .build();
        filialRepository.saveAll(List.of(filial1, filial2, filial3));
    }

}
