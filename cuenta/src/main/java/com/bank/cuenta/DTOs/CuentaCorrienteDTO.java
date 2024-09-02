package com.bank.cuenta.DTOs;

import com.bank.cuenta.models.CuentaCorriente;
import lombok.Data;

@Data
public class CuentaCorrienteDTO extends CuentaDTO {
    private Double montoDescubierto;
}
