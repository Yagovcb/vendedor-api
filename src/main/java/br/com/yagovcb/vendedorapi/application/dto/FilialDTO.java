package br.com.yagovcb.vendedorapi.application.dto;

import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilialDTO implements Serializable {
    private Long id;

    private String nome;

    @Size(min = 14, max = 14)
    private String cnpj;

    private String cidade;

    @Size(min = 2, max = 2)
    private String uf;

    private TipoFilial tipo;

    private Boolean ativo;

    private LocalDate dataCadastro;

    private LocalDate ultimaAtualizacao;
}
