package com.bank.demo.services;

import com.bank.demo.DTOs.CuentaReporte;
import com.bank.demo.DTOs.Report;
import com.bank.demo.DTOs.ReportLine;
import com.bank.demo.data.ClienteRespository;
import com.bank.demo.exceptions.ClienteNotFoundException;
import com.bank.demo.models.Cliente;
import com.bank.demo.models.Cuenta;
import com.bank.demo.models.Movimiento;
import com.bank.demo.models.Persona;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Service
public class ClienteService {

    private final ClienteRespository clienteRespository;
    private final PersonaService personaService;
    private final CuentaService cuentaService;
    private final MovimientoService movimientoService;

    public ClienteService(ClienteRespository clienteRepository, PersonaService personaService, CuentaService cuentaService, MovimientoService movimientoService) {
        this.clienteRespository = clienteRepository;
        this.personaService = personaService;
        this.cuentaService = cuentaService;
        this.movimientoService = movimientoService;
    }

    public List<Cliente> getClientes() {
        return this.clienteRespository.findAll();
    }

    public Cliente createClient(Cliente cliente) throws Exception {
        Optional<Cliente> clienteRegistrado = this.clienteRespository.findById(cliente.getClienteId());
        Optional<Persona> personaRegistrada = this.personaService.findByIdentificacion(cliente.getPersona().getIdentificacion());
        if (clienteRegistrado.isPresent() || personaRegistrada.isPresent()) {
            throw new Exception("El cliente ya existe");
        } else {
            this.personaService.savePersona(cliente.getPersona());
            return this.clienteRespository.save(cliente);

        }
    }

    public Cliente updateClient(Cliente clienteNuevo, String clienteid) throws Exception {
        Optional<Cliente> cliente = clienteRespository.findById(clienteid);
        if (cliente.isPresent()) {
            cliente.get().setEstado(clienteNuevo.getEstado());
            cliente.get().setContrasena(clienteNuevo.getContrasena());
            cliente.get().getPersona().setDireccion(clienteNuevo.getPersona().getDireccion());
            cliente.get().getPersona().setEdad(clienteNuevo.getPersona().getEdad());
            cliente.get().getPersona().setGenero(clienteNuevo.getPersona().getGenero());
            cliente.get().getPersona().setNombre(clienteNuevo.getPersona().getNombre());
            cliente.get().getPersona().setTelefono(clienteNuevo.getPersona().getTelefono());
            return this.clienteRespository.save(cliente.get());

        } else {
            throw new Exception("Cliente no encontrado");
        }
    }


    public void deleteClient(String clienteid) {
        this.clienteRespository.deleteById(clienteid);
    }

    public Optional<Cliente> findById(String clienteid) {
        return this.clienteRespository.findById(clienteid);

    }

    public Report generateReport(String clientId, LocalDate[] fecha) throws Exception {
        Timestamp startDate = Timestamp.valueOf(fecha[0].atStartOfDay().plusDays(1));
        Timestamp endDate = Timestamp.valueOf(fecha[1].atStartOfDay().plusDays(1));
        Report report = new Report();
        Optional<Cliente> cliente = clienteRespository.findById(clientId);
        if (cliente.isPresent()) {
            report.setClienteid(cliente.get().getClienteId());
            report.setCliente(cliente.get().getPersona().getNombre());
            report.setFechaReporte(new Date());
            List<Cuenta> cuentas = cuentaService.findBycliente(clientId);
            List<CuentaReporte> cuentasReporte = new ArrayList<>();
            if (!cuentas.isEmpty()) {
                for (Cuenta cuenta : cuentas) {
                    CuentaReporte cuentaReporte = new CuentaReporte();
                    cuentaReporte.setNumeroCuenta(cuenta.getNumeroCuenta());
                    cuentaReporte.setTypo(cuenta.getTipoCuenta());
                    cuentaReporte.setState(cuenta.getEstado());
                    List<ReportLine> reportLines = new ArrayList<>();
                    List<Movimiento> movimientos = movimientoService.findByCuentaAndIntervalo(cuenta.getNumeroCuenta(), startDate, endDate);
                    if (!movimientos.isEmpty()) {
                        for (Movimiento movimiento : movimientos) {
                            ReportLine line = new ReportLine(
                                    movimiento.getFecha(),
                                    movimiento.getTipoMovimiento(),
                                    movimiento.getSaldo() - movimiento.getValor(),
                                    movimiento.getValor(),
                                    movimiento.getSaldo()
                            );
                            reportLines.add(line);
                        }
                        cuentaReporte.setLineas(reportLines);
                    }
                    cuentasReporte.add(cuentaReporte);

                }

            }
            report.setCuentas(cuentasReporte);
        } else {
            throw new ClienteNotFoundException("El cliente no existe");
        }
        return report;
    }
}
