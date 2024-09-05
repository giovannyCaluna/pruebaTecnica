package com.bank.cliente.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class CrearMovimientoDTO {
    private String tipoMovimiento;
    @NotNull(message = "El valor no puede ser nulo")
    private Double valor;
    @NotNull(message = "El saldo no puede ser nulo")
    private Double saldo;
    @NotBlank(message = "El número de cuenta no puede estar en blanco")
    @Size(min = 6, max = 6, message = "El número de cuenta debe tener exactamente 6 caracteres")
    private String numeroCuenta;


    public CrearMovimientoDTO() {
    }

    public CrearMovimientoDTO(Date fecha, String tipoMovimiento, Double valor, Double saldo, String numeroCuenta) {
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.saldo = saldo;
    }

}
