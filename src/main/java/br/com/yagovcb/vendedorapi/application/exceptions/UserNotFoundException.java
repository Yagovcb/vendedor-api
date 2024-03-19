package br.com.yagovcb.vendedorapi.application.exceptions;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends APIException {

    public UserNotFoundException(APIExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public UserNotFoundException(APIExceptionCode exceptionCode, Throwable cause, String message) {
        super(exceptionCode, cause, message);
    }

    @Override
    public APIExceptionCode getExceptionCode() {
        return super.getExceptionCode();
    }
}
