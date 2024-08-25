package com.bank.demo.exceptions;

public class CuentaAlreadyCreatedException  extends RuntimeException {

    public CuentaAlreadyCreatedException(String message) {
        super(message);
    }
}
