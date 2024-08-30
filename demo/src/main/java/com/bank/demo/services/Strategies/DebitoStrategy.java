package com.bank.demo.services.Strategies;

import com.bank.demo.services.IStrategies.OperacionCuenta;

import static java.lang.Math.abs;


public class DebitoStrategy implements OperacionCuenta {

    @Override
    public double ejecutar(double saldo, double monto) {
        return saldo - abs(monto);
    }

}
