package br.com.yagovcb.vendedorapi.application.dto;

import br.com.yagovcb.vendedorapi.domain.enums.TipoContracao;
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
public class VendedorDTO implements Serializable {
    private Long id;

    @Size(min = 5, max = 15)
    private String matricula;

    private String nome;

    private LocalDate dataNascimento;

    @Size(min = 11, max = 14)
    private String documento;

    private String email;

    private TipoContracao tipoContratacao;

    private FilialDTO filial;
}
