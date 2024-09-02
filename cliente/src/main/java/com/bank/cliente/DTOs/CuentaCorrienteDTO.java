package com.bank.cliente.DTOs;

import lombok.Data;

@Data
public class CuentaCorrienteDTO extends CuentaDTO {
    private Double montoDescubierto;
}
