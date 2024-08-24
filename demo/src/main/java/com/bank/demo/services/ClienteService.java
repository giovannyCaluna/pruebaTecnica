package com.bank.demo.services;

import com.bank.demo.DTOs.CuentaReporte;
import com.bank.demo.DTOs.Report;
import com.bank.demo.DTOs.ReportLine;
import com.bank.demo.data.ClienteRespository;
import com.bank.demo.models.Cliente;
import com.bank.demo.models.Cuenta;
import com.bank.demo.models.Movimiento;
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
        this.personaService.savePersona(cliente.getPersona());
        return this.clienteRespository.save(cliente);
    }

    public Cliente updateClient(Cliente cliente) throws Exception {
        this.personaService.updatePersona(cliente.getPersona());
        return this.clienteRespository.save(cliente);
    }

    public void deleteClient(Cliente cliente) {
        this.clienteRespository.delete(cliente);
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
        }
        return report;
    }
}
