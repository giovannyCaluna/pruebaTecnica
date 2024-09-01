package com.bank.cuenta.services.Strategies;

import com.bank.cuenta.services.IStrategies.OperacionCuenta;

import static java.lang.Math.abs;


public class DebitoStrategy implements OperacionCuenta {

    @Override
    public double ejecutar(double saldo, double monto) {
        return saldo - abs(monto);
    }

}
