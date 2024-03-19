package br.com.yagovcb.vendedorapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ConstraintDTO {
    private String objectName;
    private String fieldName;
    private String validationMessage;
}
