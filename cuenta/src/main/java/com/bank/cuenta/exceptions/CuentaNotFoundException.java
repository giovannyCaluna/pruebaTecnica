package com.bank.cuenta.exceptions;

public class CuentaNotFoundException  extends RuntimeException{
    public CuentaNotFoundException(String message) {
        super(message);
    }

}
