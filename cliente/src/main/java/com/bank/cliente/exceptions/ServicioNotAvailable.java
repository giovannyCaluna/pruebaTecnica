package com.bank.cliente.exceptions;

public class ServicioNotAvailable extends RuntimeException {

    public ServicioNotAvailable(String message) {
        super(message);
    }
}

