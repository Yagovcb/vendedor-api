package br.com.yagovcb.vendedorapi.application.exceptions;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends APIException {

    public NotFoundException(String errorMessage) {
        super(APIExceptionCode.RESOURCE_NOT_FOUND, errorMessage);
    }
}
