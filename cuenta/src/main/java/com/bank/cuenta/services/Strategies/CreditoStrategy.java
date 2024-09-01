package com.bank.cuenta.services.Strategies;

import com.bank.cuenta.services.IStrategies.OperacionCuenta;


public class CreditoStrategy implements OperacionCuenta {

    @Override
    public double ejecutar(double saldo, double monto) {
        return saldo + monto;
    }
}
