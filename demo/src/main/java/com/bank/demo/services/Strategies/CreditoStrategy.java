package com.bank.demo.services.Strategies;

import com.bank.demo.services.IStrategies.OperacionCuenta;


public class CreditoStrategy implements OperacionCuenta {

    @Override
    public double ejecutar(double saldo, double monto) {
        return saldo + monto;
    }
}
