package com.bank.cuenta.services;

import com.bank.cuenta.DTOs.CrearMovimientoDTO;
import com.bank.cuenta.DTOs.MovimientoDTO;
import com.bank.cuenta.DTOs.TransferenciaDTO;
import com.bank.cuenta.DTOs.UpdateMovimientoDTO;
import com.bank.cuenta.exceptions.CuentaNotFoundException;
import com.bank.cuenta.exceptions.MovimientoNotFoundException;
import com.bank.cuenta.exceptions.SaldoInsuficienteException;
import com.bank.cuenta.models.Accion;
import com.bank.cuenta.services.IStrategies.OperacionCuenta;
import com.bank.cuenta.data.CuentasRepository;
import com.bank.cuenta.data.MovimientosRepository;
import com.bank.cuenta.models.Cuenta;
import com.bank.cuenta.models.Movimiento;
import com.bank.cuenta.services.Strategies.CreditoStrategy;
import com.bank.cuenta.services.Strategies.DebitoStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.*;

@Service
@Slf4j
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
    public Movimiento createMovimiento(CrearMovimientoDTO movimiento)
            throws SaldoInsuficienteException, CuentaNotFoundException {
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
                throw new SaldoInsuficienteException("Saldo no disponible");
            }
        } else {
            throw new CuentaNotFoundException("No existe la cuenta");
        }
    }

    public OperacionCuenta getOperacion(double monto) {
        if (monto >= 0) {
            return new CreditoStrategy();
        } else {
            return new DebitoStrategy();
        }

    }

    public Movimiento updateMovimiento(UpdateMovimientoDTO uppdateMovimiento, Long movimientoId) throws MovimientoNotFoundException {
        Optional<Movimiento> prevMovimiento = this.movimientosRepository.findById(movimientoId);
        if (prevMovimiento.isPresent()) {
           prevMovimiento.get().setTipoMovimiento(uppdateMovimiento.getTipoMovimiento());
            return this.movimientosRepository.save(prevMovimiento.get());
        } else {
            throw new MovimientoNotFoundException("No existe el movimiento");
        }
    }

    public void deleteMovimiento(Long movimiento) throws MovimientoNotFoundException {
        Optional<Movimiento> removeMovimiento = this.movimientosRepository.findById(movimiento);

        if (removeMovimiento.isPresent()) {
            this.movimientosRepository.delete(removeMovimiento.get());
        } else {
            throw new MovimientoNotFoundException("Movimiento no existe");
        }
    }

    public List<Movimiento> getMovimientosByCuenta(String cuenta) {
        return this.movimientosRepository.findByCuenta_NumeroCuentaOrderByFechaDesc(cuenta);
    }

    private Boolean validateFunds(Double saldo, Double valorTransaccion) {
        return saldo + valorTransaccion >= 0;
    }

    public Movimiento setPrimerMovimiento(Movimiento movimiento) {
        return this.movimientosRepository.save(movimiento);
    }

    public List<Movimiento> findByCuentaAndIntervalo(String id, Date start, Date end) {
        return this.movimientosRepository.findByCuenta_NumeroCuentaAndAndFechaBetweenOrderByFechaDesc(id, start, end);

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<Movimiento> realizarTransferencia(TransferenciaDTO transferenciaDTO)  {
        try{
            CrearMovimientoDTO debMovimiento = new CrearMovimientoDTO();
            CrearMovimientoDTO acredMovimiento = new CrearMovimientoDTO();
            debMovimiento.setTipoMovimiento("Transferencia");
            debMovimiento.setNumeroCuenta(transferenciaDTO.getCuentaOrigen());
            debMovimiento.setValor(transferenciaDTO.getMonto() * (-1));
            acredMovimiento.setTipoMovimiento("Transferencia");
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

        }catch(CuentaNotFoundException ex ){
            log.info("Error al realizar la transferencia. Una de las cuentas no existe: {}", ex.getMessage());
            throw ex;

        }catch (SaldoInsuficienteException ex){
            log.info("Error al realizar la transferencia. El saldo de la cuenta origen es menor a la cantidad del monto: {}", ex.getMessage());
            throw ex;

        } catch (Exception ex) {
            log.error("Error al realizar la transferencia: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }


    }
}
