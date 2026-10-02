package com.uc.ms_security.exception;

//Personaliar las excepciones de la aplicación, para que sean más fáciles de manejar y entender.
public class ApplicationException extends RuntimeException {

    private final ErrorCase errorCase;

    public ApplicationException(ErrorCase errorCase, String message) {
        super(message);
        this.errorCase = errorCase;
    }

    public ErrorCase getErrorCase() {
        return errorCase;
    }
}
