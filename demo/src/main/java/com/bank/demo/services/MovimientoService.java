package com.bank.demo.services;

import com.bank.demo.DTOs.MovimientoDTO;
import com.bank.demo.data.CuentasRepository;
import com.bank.demo.data.MovimientosRepository;
import com.bank.demo.models.Cuenta;
import com.bank.demo.models.Movimiento;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovimientoService {

    private final MovimientosRepository movimientosRepository;
    private final CuentasRepository cuentasRepository;

    public MovimientoService(MovimientosRepository movimientosRepository, CuentasRepository cuentasRepository) {
        this.movimientosRepository = movimientosRepository;
        this.cuentasRepository = cuentasRepository;
    }

    public List<Movimiento> getAllMovimientos() {
        return this.movimientosRepository.findAll();
    }

    public Movimiento createMovimiento(MovimientoDTO movimiento) throws Exception {
        Optional<Cuenta> cuenta = this.cuentasRepository.findById(movimiento.getNumeroCuenta());
        if (cuenta.isPresent()) {
            Movimiento lastMovimiento = this.movimientosRepository.findFirstByCuenta_NumeroCuentaOrderByFechaDesc(movimiento.getNumeroCuenta());
            if (validateFunds(lastMovimiento.getSaldo(), movimiento.getValor())) {
                Movimiento move = new Movimiento();
                move.setTipoMovimiento(movimiento.getTipoMovimiento());
                move.setFecha(new Date());
                move.setSaldo(lastMovimiento.getSaldo() + movimiento.getValor());
                move.setValor(movimiento.getValor());
                move.setCuenta(cuenta.get());
                return this.movimientosRepository.save(move);
            } else {
                throw new Exception("Saldo no disponible");
            }
        } else {
            throw new Exception("No existe la cuenta");
        }
    }

    public Movimiento updateMovimiento(MovimientoDTO movimiento, Long movimientoId) throws Exception {
        Optional<Movimiento> prevMovimiento = this.movimientosRepository.findById(movimientoId);
        if (prevMovimiento.isPresent()) {
            prevMovimiento.get().setTipoMovimiento(movimiento.getTipoMovimiento());
            prevMovimiento.get().setFecha(new Date());
            return this.movimientosRepository.save(prevMovimiento.get());
        } else {
            throw new Exception("No existe el movimiento");
        }

    }

    public void deleteMovimiento(Long movimiento) {
        this.movimientosRepository.deleteById(movimiento);
    }

    public List<Movimiento> getMovimientosByCuenta(String cuenta) {
        return this.movimientosRepository.findByCuenta_NumeroCuentaOrderByFechaDesc(cuenta);
    }

    private Boolean validateFunds(Double saldo, Double valorTransaccion) {
        if (saldo + valorTransaccion >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public Movimiento setPrimerMovimiento(Movimiento movimiento) {
        return this.movimientosRepository.save(movimiento);
    }

    public List<Movimiento> findByCuentaAndIntervalo(String id, Date start, Date end) {
        return this.movimientosRepository.findByCuenta_NumeroCuentaAndAndFechaBetweenOrderByFechaDesc(id, start, end);

    }
}
