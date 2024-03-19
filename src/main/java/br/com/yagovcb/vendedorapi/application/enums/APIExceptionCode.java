package br.com.yagovcb.vendedorapi.application.enums;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum APIExceptionCode {
    // Codes for generic exceptions
    UNKNOWN("GE-000"),
    UNAUTHENTICATED("GE-001"),

    // Codes for business exceptions
    RESOURCE_NOT_FOUND("BE-001"),
    RESOURCE_ALREADY_EXISTS("BE-002"),
    EMAIL_ALREADY_EXISTS("BE-004"),

    NON_STANDARD_EMAIL("BE-017"),

    // Codes validations
    CONSTRAINT_VALIDATION("V-001"),
    VIOLATION_REFERENCE("V-002"),
    PASSWORD_VALIDATION("V-003"),
    INVALID_CREDENTIALS("AUTH-001"),
    USER_IS_NOT_ACTIVE("AUTH-002"),
    ACCESS_FORBIDDEN("AUTH-003"),

    SECURITY_NAAT_VERIFICATION("AUTH-009");


    @NonNull
    private final String key;
}
