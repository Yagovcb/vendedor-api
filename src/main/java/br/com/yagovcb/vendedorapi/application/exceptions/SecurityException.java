package br.com.yagovcb.vendedorapi.application.exceptions;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import lombok.Getter;


public class SecurityException extends RuntimeException {

	@Getter
	private APIExceptionCode exceptionCode;
	
	public SecurityException(APIExceptionCode exceptionCode) {
		super(exceptionCode.getKey());
		this.exceptionCode = exceptionCode; 
	}
}
