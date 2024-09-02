package com.bank.cuenta.services;


import com.bank.cuenta.DTOs.CuentaDTO;
import com.bank.cuenta.data.ClienteRespository;
import com.bank.cuenta.data.CuentasRepository;
import com.bank.cuenta.exceptions.ClienteNotFoundException;
import com.bank.cuenta.exceptions.CuentaAlreadyCreatedException;
import com.bank.cuenta.exceptions.CuentaNotFoundException;
import com.bank.cuenta.models.*;
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

    public Cuenta createCuenta(CuentaDTO cuentaDTO) throws Exception {
        Optional<Cliente> cliente = this.clienteRespository.findById(cuentaDTO.getClienteId());
        Optional<Cuenta> cuentaExistente = this.cuentasRepository.findById(cuentaDTO.getNumeroCuenta());
        if (cliente.isPresent()) {
            if (cuentaExistente.isEmpty()) {
                Cuenta nuevaCuenta;
                if ("CORRIENTE".equalsIgnoreCase(cuentaDTO.getTipoCuenta())) {
                    CuentaCorriente cuentaCorriente = new CuentaCorriente();
                    cuentaCorriente.setMontoDescubierto(0.0);
                    nuevaCuenta = cuentaCorriente;
                } else if ("AHORROS".equalsIgnoreCase(cuentaDTO.getTipoCuenta())) {
                    CuentaAhorros cuentaAhorros = new CuentaAhorros();
                    cuentaAhorros.setInteres(0.0);
                    nuevaCuenta = cuentaAhorros;
                } else {
                    throw new Exception("Tipo de cuenta no soportado");
                }
                nuevaCuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
                nuevaCuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
                nuevaCuenta.setEstado(cuentaDTO.getEstado());
                nuevaCuenta.setCliente(cliente.get());
                nuevaCuenta = this.cuentasRepository.save(nuevaCuenta);
                Movimiento primerMovimiento = new Movimiento();
                primerMovimiento.setTipoMovimiento("Deposito");
                primerMovimiento.setCuenta(nuevaCuenta);
                primerMovimiento.setValor(cuentaDTO.getSaldoInicial());
                primerMovimiento.setSaldo(cuentaDTO.getSaldoInicial());
                primerMovimiento.setFecha(new Date());
                movimientoService.setPrimerMovimiento(primerMovimiento);
                return nuevaCuenta;
            } else {
                throw new CuentaAlreadyCreatedException("Cuenta ya existe.");
            }

        } else {
            throw new ClienteNotFoundException("Cliente no encontrado");
        }
    }

    public Cuenta updateCuenta(CuentaDTO cuentaDTO, String cuentaid) throws Exception {
        Optional<Cuenta> cuentaExistente = this.cuentasRepository.findById(cuentaid);
        if (cuentaExistente.isPresent()) {
            cuentaExistente.get().setEstado(cuentaDTO.getEstado());
            return this.cuentasRepository.save(cuentaExistente.get());
        } else {
            throw new CuentaNotFoundException("Cuenta no encontrada");
        }
    }

    public void deleteCuenta(String cuentaid) throws Exception {
        Optional<Cuenta> removeCuenta = this.cuentasRepository.findById(cuentaid);
        if (removeCuenta.isPresent()) {
            this.cuentasRepository.delete(removeCuenta.get());
        } else {
            throw new CuentaNotFoundException("Cuenta no existe");
        }
    }

    public List<Cuenta> findBycliente(String clienteId) {
        return this.cuentasRepository.findByCliente_ClienteId(clienteId);
    }

    public Optional<Cuenta> findById(String id) {
        return this.cuentasRepository.findById(id);
    }

}
