package com.bank.cliente.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CuentaUpdateDTO {
    private Double saldoInicial;
    @NotNull(message = "Tienes que especificar el estado de la cuenta: TRUE: Activo, FALSE:Inactivo")
    private Boolean estado;
    private Double montoDescubierto;
    private Double interes;
    @NotNull(message = "El tipo de cuenta se debe especificar: AHORROS o CORRIENTE")
    private String tipoCuenta;
}
