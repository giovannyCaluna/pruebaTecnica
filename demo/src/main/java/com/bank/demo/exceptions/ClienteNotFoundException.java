package com.bank.demo.exceptions;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(String message) {
        super(message);
    }
}
