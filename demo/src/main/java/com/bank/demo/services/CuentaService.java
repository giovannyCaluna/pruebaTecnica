package com.bank.demo.services;

import com.bank.demo.DTOs.CuentaDTO;
import com.bank.demo.data.ClienteRespository;
import com.bank.demo.data.CuentasRepository;
import com.bank.demo.exceptions.ClienteNotFoundException;
import com.bank.demo.models.Cliente;
import com.bank.demo.models.Cuenta;
import com.bank.demo.models.Movimiento;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {


    private final CuentasRepository cuentasRepository;
    private final MovimientoService movimientoService;
    private final ClienteRespository clienteRespository;

    public CuentaService(CuentasRepository cuentasRepository, ClienteRespository clienteRespository, MovimientoService movimientoService) {
        this.cuentasRepository = cuentasRepository;
        this.clienteRespository = clienteRespository;
        this.movimientoService = movimientoService;
    }

    public List<Cuenta> getAllCuentas() {
        return this.cuentasRepository.findAll();
    }

    public Cuenta createCuenta(CuentaDTO cuenta) throws Exception {
        Optional<Cliente> cliente = this.clienteRespository.findById(cuenta.getClienteId());
        if (cliente.isPresent()) {
            Cuenta nuevaCuenta = new Cuenta(cuenta.getCuentaid(),cuenta.getTipo(),cuenta.getSaldoInicial(),cuenta.getEstado(),cliente.get());
            nuevaCuenta = this.cuentasRepository.save(nuevaCuenta);
            Movimiento primerMovimiento = new Movimiento();
            primerMovimiento.setTipoMovimiento("Deposito");
            primerMovimiento.setCuenta(nuevaCuenta);
            primerMovimiento.setValor(cuenta.getSaldoInicial());
            primerMovimiento.setSaldo(cuenta.getSaldoInicial());
            primerMovimiento.setFecha(new Date());
            movimientoService.setPrimerMovimiento(primerMovimiento);
            return nuevaCuenta;
        } else {
            throw new ClienteNotFoundException("Cliente no encontrado++");
        }
    }

    public Cuenta updateCuenta(CuentaDTO cuenta) throws Exception {
        Optional<Cuenta> updateCuenta = this.cuentasRepository.findById(cuenta.getCuentaid());
        if (updateCuenta.isPresent()) {
            updateCuenta.get().setTipoCuenta(cuenta.getTipo());
            updateCuenta.get().setSaldoInicial(cuenta.getSaldoInicial());
            updateCuenta.get().setEstado(cuenta.getEstado());
            updateCuenta.get().setNumeroCuenta(cuenta.getCuentaid());
            return this.cuentasRepository.save(updateCuenta.get());
        } else {
            throw new Exception("Cliente no encontrado");
        }
    }

    public void deleteCuenta(CuentaDTO cuenta) throws Exception {
        Optional<Cuenta> removeCuenta = this.cuentasRepository.findById(cuenta.getCuentaid());
        if (removeCuenta.isPresent()) {
            this.cuentasRepository.delete(removeCuenta.get());
        } else {
            throw new Exception("Cuenta no existe");
        }
    }

    public List<Cuenta> findBycliente(String clienteId) {
        return this.cuentasRepository.findByCliente_ClienteId(clienteId);
    }

    public Optional<Cuenta> findById(String id) {
        return this.cuentasRepository.findById(id);
    }

}
