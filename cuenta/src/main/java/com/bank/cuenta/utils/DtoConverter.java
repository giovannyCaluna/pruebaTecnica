package com.bank.cuenta.utils;

import com.bank.cuenta.DTOs.CuentaAhorrosDTO;
import com.bank.cuenta.DTOs.CuentaCorrienteDTO;
import com.bank.cuenta.DTOs.CuentaDTO;
import com.bank.cuenta.DTOs.MovimientoDTO;
import com.bank.cuenta.models.Cuenta;
import com.bank.cuenta.models.CuentaAhorros;
import com.bank.cuenta.models.CuentaCorriente;
import com.bank.cuenta.models.Movimiento;

import java.util.List;

public class DtoConverter {


    public static CuentaDTO convertToCuentaDto(Cuenta cuenta, List<MovimientoDTO> movimientosDto) throws Exception {

        if (cuenta instanceof CuentaCorriente) {
            return getCuentaCorrienteDTO(cuenta, movimientosDto);

        } else if (cuenta instanceof CuentaAhorros) {
            return getCuentaAhorrosDTO(cuenta, movimientosDto);

        } else {
            throw new Exception("Tipo de cuenta no coincide con el existente");
        }

    }

    private static CuentaAhorrosDTO getCuentaAhorrosDTO(Cuenta cuenta, List<MovimientoDTO> movimientosDto) {
        CuentaAhorrosDTO cuentaAhorrosDTO = new CuentaAhorrosDTO();
        cuentaAhorrosDTO.setInteres(0.0);
        cuentaAhorrosDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaAhorrosDTO.setClienteId(cuenta.getCliente().getClienteId());
        cuentaAhorrosDTO.setMovimientos(movimientosDto);
        cuentaAhorrosDTO.setEstado(cuenta.getEstado());
        cuentaAhorrosDTO.setTipoCuenta("CORRIENTE");
        cuentaAhorrosDTO.setSaldoInicial(cuenta.getSaldoInicial());
        return cuentaAhorrosDTO;
    }

    private static CuentaCorrienteDTO getCuentaCorrienteDTO(Cuenta cuenta, List<MovimientoDTO> movimientosDto) {
        CuentaCorrienteDTO cuentaCorrienteDTO = new CuentaCorrienteDTO();
        cuentaCorrienteDTO.setMontoDescubierto(0.0);
        cuentaCorrienteDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaCorrienteDTO.setClienteId(cuenta.getCliente().getClienteId());
        cuentaCorrienteDTO.setMovimientos(movimientosDto);
        cuentaCorrienteDTO.setEstado(cuenta.getEstado());
        cuentaCorrienteDTO.setTipoCuenta("CORRIENTE");
        cuentaCorrienteDTO.setSaldoInicial(cuenta.getSaldoInicial());
        return cuentaCorrienteDTO;
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


