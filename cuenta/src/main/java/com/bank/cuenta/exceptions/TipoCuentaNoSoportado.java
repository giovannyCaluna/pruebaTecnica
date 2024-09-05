package com.bank.cuenta.exceptions;


public class TipoCuentaNoSoportado extends RuntimeException {
    public TipoCuentaNoSoportado(String mensaje) {
        super(mensaje);

    }
}