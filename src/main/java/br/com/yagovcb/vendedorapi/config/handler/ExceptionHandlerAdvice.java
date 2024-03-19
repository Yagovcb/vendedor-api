package br.com.yagovcb.vendedorapi.config.handler;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.APIException;
import br.com.yagovcb.vendedorapi.application.exceptions.BusinessException;
import br.com.yagovcb.vendedorapi.application.exceptions.ConflictException;
import br.com.yagovcb.vendedorapi.application.exceptions.InvalidJwtAuthenticationException;
import br.com.yagovcb.vendedorapi.infrastructure.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;

	@Autowired
	public ExceptionHandlerAdvice(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler({ BusinessException.class})
	public final ResponseEntity<Object> handlerBusinessException(APIException ex, Locale locale){
		MessageResponse build = makeErrorRespondeBuilder(ex, locale);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(build);
	}

	@ExceptionHandler({ ConflictException.class})
	public final ResponseEntity<Object> handlerConflictException(APIException ex, Locale locale){
		MessageResponse build = makeErrorRespondeBuilder(ex, locale);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(build);
	}

	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<BusinessException> invalidJwtAuthenticationException(Exception ex, WebRequest request) {
		BusinessException exceptionResponse = new BusinessException(APIExceptionCode.INVALID_CREDENTIALS, ex.getMessage());
		return ResponseEntity.badRequest().body(exceptionResponse);
	}

	private MessageResponse makeErrorRespondeBuilder(APIException ex, Locale locale){
		String msgKey = makeMessageKey(ex.getExceptionCode());
		String message = messageSource.getMessage(msgKey, null, ex.getMessage(), locale);
		if(message == null) {
			msgKey = APIExceptionCode.UNKNOWN.getKey();
			message = messageSource.getMessage(msgKey, null, null, locale);
		}
		logger.error(ex.getMessage());
		return MessageResponse.builder().code(msgKey).message(message).constraints(null).build();
	}

	private String makeMessageKey(APIExceptionCode exceptionCode) {
		return exceptionCode != null ? exceptionCode.getKey() : APIExceptionCode.UNKNOWN.getKey();
	}
}
