package com.bank.cliente.exceptions;

public class CuentaAlreadyCreatedException  extends RuntimeException {

    public CuentaAlreadyCreatedException(String message) {
        super(message);
    }
}
