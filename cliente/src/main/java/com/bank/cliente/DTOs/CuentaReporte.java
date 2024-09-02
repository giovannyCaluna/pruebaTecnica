package com.bank.cliente.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class CuentaReporte {
    @NotBlank(message = "El número de cuenta no puede estar en blanco")
    @Size(min = 6, max = 6, message = "El número de cuenta debe tener exactamente 6 caracteres")
    private String numeroCuenta;
    private String tipo;
    private Boolean state;
    private List<ReportLine> lineas;

    public CuentaReporte() {

    }

    public CuentaReporte(String numeroCuenta, String tipo, Boolean state, List<ReportLine> lineas) {
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.state = state;
        this.lineas = lineas;
    }


}
