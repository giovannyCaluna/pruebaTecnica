package com.bank.cliente.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CuentaDTO {
    @NotBlank(message = "El número de cuenta no puede estar en blanco")
    @Size(min = 6, max = 6, message = "El número de cuenta debe tener exactamente 6 caracteres")
    private String numeroCuenta;
    @NotBlank(message = "El tipo de cuenta no puede estar en blanco")
    private String tipoCuenta;
    @NotNull(message = "El saldo inicial no puede ser nulo")
    @Min(value = 0, message = "El saldo inicial no puede ser negativo")
    private Double saldoInicial;
    @NotNull(message = "El estado de la cuenta no puede ser nulo")
    private Boolean estado;
    @NotNull(message = "El ID del cliente no puede ser nulo")
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
