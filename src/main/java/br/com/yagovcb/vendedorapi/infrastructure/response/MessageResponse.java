package br.com.yagovcb.vendedorapi.infrastructure.response;

import br.com.yagovcb.vendedorapi.application.dto.ConstraintDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponse {

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "constraints")
    private List<ConstraintDTO> constraints;
}
