package br.com.yagovcb.vendedorapi.infrastructure.integration.response;

import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilialResponse {
    @JsonProperty(value = "nome")
    private String nome;
    @JsonProperty(value = "cnpj")
    private String cnpj;
    @JsonProperty(value = "tipo")
    private TipoFilial tipo;
}
