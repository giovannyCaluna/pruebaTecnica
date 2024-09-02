package com.bank.cuenta.DTOs;

import com.bank.cuenta.models.CuentaAhorros;
import com.bank.cuenta.models.CuentaCorriente;
import lombok.Data;
@Data
public class CuentaAhorrosDTO extends CuentaDTO {
    private Double interes;

}


