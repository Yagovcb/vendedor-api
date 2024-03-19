package br.com.yagovcb.vendedorapi.infrastructure.request;

import br.com.yagovcb.vendedorapi.domain.enums.TipoContracao;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CadastroVendedorRequest {
    @Size(min = 5, max = 15)
    @JsonProperty(value = "matricula")
    private String matricula;

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "data_nascimento")
    private LocalDate dataNascimento;

    @Size(min = 11, max = 14)
    @JsonProperty(value = "documento")
    private String documento;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "tipo_contratacao")
    private TipoContracao tipoContratacao;
}
