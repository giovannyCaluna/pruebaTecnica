package com.bank.cuenta.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class CuentaDTO {


    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private String clienteId;
    private List<MovimientoDTO> movimientos;


    public CuentaDTO() {
    }

    public CuentaDTO(String numeroCuenta, String tipoCuenta, Double saldoInicial, Boolean estado, String clienteId, List<MovimientoDTO> movimientos) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoInicial = saldoInicial;
        this.estado = estado;
        this.clienteId = clienteId;
        this.movimientos = movimientos;
    }


}
