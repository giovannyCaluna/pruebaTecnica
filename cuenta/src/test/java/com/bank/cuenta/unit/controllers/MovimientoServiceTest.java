package com.bank.cuenta.unit.controllers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.Optional;

import com.bank.cuenta.DTOs.MovimientoDTO;
import com.bank.cuenta.data.CuentasRepository;
import com.bank.cuenta.data.MovimientosRepository;
import com.bank.cuenta.exceptions.CuentaNotFoundException;
import com.bank.cuenta.exceptions.SaldoInsuficienteException;
import com.bank.cuenta.models.Cuenta;
import com.bank.cuenta.models.Movimiento;
import com.bank.cuenta.services.MovimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTest {

    @Mock
    private CuentasRepository cuentasRepository;

    @Mock
    private MovimientosRepository movimientosRepository;

    @InjectMocks
    private MovimientoService movimientoService;

    private MovimientoDTO movimientoDTO;
    private Cuenta cuenta;
    private Movimiento lastMovimiento;
    private Movimiento newMovimiento;

    @BeforeEach
    void setUp() {
        movimientoDTO = new MovimientoDTO();
        movimientoDTO.setNumeroCuenta("123456");
        movimientoDTO.setValor(500.0);
        movimientoDTO.setTipoMovimiento("Transferencia");

        cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setSaldoInicial(1000.0);

        lastMovimiento = new Movimiento();
        lastMovimiento.setSaldo(1000.0);
        lastMovimiento.setFecha(new Date());
        lastMovimiento.setTipoMovimiento("Deposito");

        newMovimiento = new Movimiento();


    }

    @Test
    @Transactional
    void testCreateMovimiento() throws Exception {
        // Given
        String numeroCuenta = "123456";
        double valor = 100.0;
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setNumeroCuenta(numeroCuenta);
        movimientoDTO.setValor(valor);
        movimientoDTO.setTipoMovimiento("Transferencia");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setSaldoInicial(500.0);
        cuenta.setEstado(true);

        Movimiento lastMovimiento = new Movimiento();
        lastMovimiento.setSaldo(500.0);

        Movimiento savedMovimiento = new Movimiento();
        savedMovimiento.setTipoMovimiento("Transferencia");
        savedMovimiento.setFecha(new Date());
        savedMovimiento.setSaldo(600.0); // Assuming operation increases saldo
        savedMovimiento.setValor(valor);
        savedMovimiento.setCuenta(cuenta);

        // Mock behavior
        when(cuentasRepository.findById(numeroCuenta)).thenReturn(Optional.of(cuenta));
        when(movimientosRepository.findFirstByCuenta_NumeroCuentaOrderByFechaDesc(numeroCuenta)).thenReturn(lastMovimiento);
        when(movimientosRepository.save(any(Movimiento.class))).thenReturn(savedMovimiento);

        // When
        Movimiento result = movimientoService.createMovimiento(movimientoDTO);

        // Then
        assertNotNull(result);
        assertEquals("Transferencia", result.getTipoMovimiento());
        assertEquals(600.0, result.getSaldo());
        assertEquals(valor, result.getValor());
        assertEquals(cuenta, result.getCuenta());

        // Verify interactions
        verify(cuentasRepository).findById(numeroCuenta);
        verify(movimientosRepository).findFirstByCuenta_NumeroCuentaOrderByFechaDesc(numeroCuenta);
        verify(movimientosRepository).save(any(Movimiento.class));
    }

    @Test
    void testCreateMovimiento_CuentaNotFound() {
        // Arrange
        when(cuentasRepository.findById(movimientoDTO.getNumeroCuenta())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CuentaNotFoundException.class, () -> {
            movimientoService.createMovimiento(movimientoDTO);
        });

        verify(movimientosRepository, never()).save(any(Movimiento.class));
    }

    @Test
    void testCreateMovimiento_SaldoInsuficiente() {
        //Fijar un valor negativo para asegurar el debito.
        movimientoDTO.setValor(-150.00);
        when(cuentasRepository.findById(movimientoDTO.getNumeroCuenta())).thenReturn(Optional.of(cuenta));
        lastMovimiento.setSaldo(100.0); // set saldo menor que movimientoDTO.getValor()
        when(movimientosRepository.findFirstByCuenta_NumeroCuentaOrderByFechaDesc(movimientoDTO.getNumeroCuenta()))
                .thenReturn(lastMovimiento);
        assertThrows(SaldoInsuficienteException.class, () -> {
            movimientoService.createMovimiento(movimientoDTO);
        });
        verify(movimientosRepository, never()).save(any(Movimiento.class));
    }
}
