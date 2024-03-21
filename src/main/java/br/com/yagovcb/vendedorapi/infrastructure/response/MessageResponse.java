package br.com.yagovcb.vendedorapi.infrastructure.response;

import br.com.yagovcb.vendedorapi.application.dto.ConstraintDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "constraints")
    private List<ConstraintDTO> constraints;
}
