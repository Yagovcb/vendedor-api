package br.com.yagovcb.vendedorapi.application.exceptions;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends APIException {

	public BadRequestException(String errorMessage) {
		super(APIExceptionCode.RESOURCE_ALREADY_EXISTS, errorMessage);
	}
}
