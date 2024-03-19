package br.com.yagovcb.vendedorapi.application.dto;

import br.com.yagovcb.vendedorapi.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private long userId;
    private String email;
    private LocalDate dataCadastro;
    private Set<Role> roles;
}
