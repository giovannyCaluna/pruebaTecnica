package com.bank.cuenta.exceptions;

public class CuentaAlreadyCreatedException  extends RuntimeException {

    public CuentaAlreadyCreatedException(String message) {
        super(message);
    }
}
