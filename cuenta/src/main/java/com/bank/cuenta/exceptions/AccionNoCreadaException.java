package com.bank.cuenta.exceptions;

public class AccionNoCreadaException extends RuntimeException {
    public AccionNoCreadaException(String mensaje) {
        super(mensaje);

    }
}
