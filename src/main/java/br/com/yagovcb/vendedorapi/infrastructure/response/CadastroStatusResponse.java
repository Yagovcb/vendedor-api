package br.com.yagovcb.vendedorapi.infrastructure.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CadastroStatusResponse {
    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "mensagem")
    private String mensagem;

    @JsonProperty(value = "vendedor")
    private VendedorResponse vendedor;
}
