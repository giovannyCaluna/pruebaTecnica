package com.bank.cuenta.utils;

import com.bank.cuenta.DTOs.CuentaDTO;
import com.bank.cuenta.DTOs.MovimientoDTO;
import com.bank.cuenta.models.Cuenta;
import com.bank.cuenta.models.Movimiento;

import java.util.List;

public class DtoConverter {


    public static CuentaDTO convertToCuentaDto(Cuenta cuenta, List<MovimientoDTO> movimientosDto) {
        return new CuentaDTO(
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoInicial(),
                cuenta.getEstado(),
                cuenta.getCliente().getClienteId(),
                movimientosDto
        );
    }

    public static MovimientoDTO convertToMovimientoDto(Movimiento movimiento) {
        return new MovimientoDTO(
                movimiento.getId(),
                movimiento.getFecha(),
                movimiento.getTipoMovimiento(),
                movimiento.getValor(),
                movimiento.getSaldo(),
                movimiento.getCuenta().getNumeroCuenta()
        );
    }
}


