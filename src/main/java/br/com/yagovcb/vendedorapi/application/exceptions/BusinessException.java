package br.com.yagovcb.vendedorapi.application.exceptions;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends APIException {

	public BusinessException(APIExceptionCode exceptionCode, String message) {
		super(exceptionCode, message);
	}

	public BusinessException(APIExceptionCode exceptionCode, Throwable cause, String message) {
		super(exceptionCode, cause, message);
	}
	
	@Override
	public APIExceptionCode getExceptionCode() {
		return super.getExceptionCode();
	}	
}
