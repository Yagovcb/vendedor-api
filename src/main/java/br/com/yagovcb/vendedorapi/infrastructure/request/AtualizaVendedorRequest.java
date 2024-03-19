package br.com.yagovcb.vendedorapi.infrastructure.request;

import br.com.yagovcb.vendedorapi.domain.enums.TipoContracao;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AtualizaVendedorRequest {

    @JsonProperty(value = "nome")
    private String nome;

    @Size(min = 11, max = 14)
    @JsonProperty(value = "documento")
    private String documento;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "tipo_contratacao")
    private TipoContracao tipoContratacao;
}
