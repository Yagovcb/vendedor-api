package br.com.yagovcb.vendedorapi.application.exceptions;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import lombok.Getter;

public abstract class APIException extends RuntimeException {
	
	@Getter
	private APIExceptionCode exceptionCode;

	public APIException(APIExceptionCode exceptionCode, String message) {
		super(message);
		this.exceptionCode = exceptionCode;
	}

	public APIException(APIExceptionCode exceptionCode, Throwable cause, String message) {
		super(message, cause);
		this.exceptionCode = exceptionCode;
	}
}
