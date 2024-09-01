package com.bank.cuenta.DTOs;

import lombok.Data;

import java.util.Date;
@Data
public class MovimientoDTO {

    private Long id;
    private Date fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
    private String numeroCuenta;


    public MovimientoDTO() {
    }
    public MovimientoDTO(Long id,Date fecha, String tipoMovimiento, Double valor, Double saldo, String numeroCuenta) {
        this.id = id;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.saldo = saldo;
        this.numeroCuenta = numeroCuenta;
    }

}
