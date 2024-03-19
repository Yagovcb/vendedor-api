package br.com.yagovcb.vendedorapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
    private String username;
}
