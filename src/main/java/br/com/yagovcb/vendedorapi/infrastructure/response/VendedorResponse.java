package br.com.yagovcb.vendedorapi.infrastructure.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendedorResponse {
    @JsonProperty(value = "matricula")
    private String matricula;

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "data_nascimento")
    private LocalDate dataNascimento;

    @JsonProperty(value = "documento")
    private String documento;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "nome_filial")
    private String nomeFilial;
}
