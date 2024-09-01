package com.bank.cuenta.services;

import com.bank.cuenta.DTOs.MovimientoDTO;
import com.bank.cuenta.DTOs.TransferenciaDTO;
import com.bank.cuenta.models.Accion;
import com.bank.cuenta.services.IStrategies.OperacionCuenta;
import com.bank.cuenta.data.CuentasRepository;
import com.bank.cuenta.data.MovimientosRepository;
import com.bank.cuenta.models.Cuenta;
import com.bank.cuenta.models.Movimiento;
import com.bank.cuenta.services.Strategies.CreditoStrategy;
import com.bank.cuenta.services.Strategies.DebitoStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MovimientoService {

    private final MovimientosRepository movimientosRepository;
    private final CuentasRepository cuentasRepository;
    private final AccionService accionService;

    public MovimientoService(MovimientosRepository movimientosRepository, CuentasRepository cuentasRepository, AccionService accionService) {
        this.movimientosRepository = movimientosRepository;
        this.cuentasRepository = cuentasRepository;
        this.accionService = accionService;
    }

    public List<Movimiento> getAllMovimientos() {
        return this.movimientosRepository.findAll();
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public Movimiento createMovimiento(MovimientoDTO movimiento)
            throws Exception {
        Optional<Cuenta> cuenta = this.cuentasRepository.findById(movimiento.getNumeroCuenta());
        if (cuenta.isPresent()) {
            Movimiento lastMovimiento = this.movimientosRepository.findFirstByCuenta_NumeroCuentaOrderByFechaDesc(movimiento.getNumeroCuenta());
            OperacionCuenta operacionCuenta = getOperacion(movimiento.getValor());
            if (validateFunds(lastMovimiento.getSaldo(), movimiento.getValor())) {
                Movimiento move = new Movimiento();
                move.setTipoMovimiento(movimiento.getTipoMovimiento());
                move.setFecha(new Date());
                move.setSaldo(operacionCuenta.ejecutar(lastMovimiento.getSaldo(), movimiento.getValor()));
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

    public OperacionCuenta getOperacion(double monto) {
        if (monto >= 0) {
            return new CreditoStrategy();
        } else {
            return new DebitoStrategy();
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

    public void deleteMovimiento(Long movimiento) throws Exception {
        Optional<Movimiento> removeMovimiento = this.movimientosRepository.findById(movimiento);

        if (removeMovimiento.isPresent()) {
            this.movimientosRepository.delete(removeMovimiento.get());
        } else {
            throw new Exception("Movimiento no existe");
        }
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


    @Transactional(propagation = Propagation.REQUIRED)
    public List<Movimiento> realizarTransferencia(TransferenciaDTO transferenciaDTO) throws Exception {
        MovimientoDTO debMovimiento = new MovimientoDTO();
        MovimientoDTO acredMovimiento = new MovimientoDTO();
        debMovimiento.setTipoMovimiento("Transferencia");
        debMovimiento.setFecha(new Date());
        debMovimiento.setNumeroCuenta(transferenciaDTO.getCuentaOrigen());
        debMovimiento.setValor(transferenciaDTO.getMonto() * (-1));
        acredMovimiento.setTipoMovimiento("Transferencia");
        acredMovimiento.setFecha(new Date());
        acredMovimiento.setNumeroCuenta(transferenciaDTO.getCuentaDestino());
        acredMovimiento.setValor(transferenciaDTO.getMonto());
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(createMovimiento(debMovimiento));
        movimientos.add(createMovimiento(acredMovimiento));
        Accion accion = new Accion();
        accion.setClienteid(transferenciaDTO.getClienteId());
        accion.setFecha(new Date());
        accion.setTipoMovimiento("Transferncia");
        accionService.create(accion);
       return movimientos;

    }
}
