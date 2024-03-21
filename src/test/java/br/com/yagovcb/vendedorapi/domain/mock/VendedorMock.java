package br.com.yagovcb.vendedorapi.domain.mock;

import br.com.yagovcb.vendedorapi.domain.enums.TipoContracao;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.model.Vendedor;

import java.time.LocalDate;

public class VendedorMock {

    public static Vendedor getVendedorMock_withFilial(Filial filial){
        return Vendedor.builder()
                .id(1L)
                .dataNascimento(LocalDate.of(1970, 1, 1))
                .documento("88717755204")
                .email("jane.doe@example.org")
                .filial(filial)
                .matricula("0000001-OUT")
                .nome("Vendedor Teste")
                .tipoContratacao(TipoContracao.OUTSOURCING)
                .build();
    }
}
