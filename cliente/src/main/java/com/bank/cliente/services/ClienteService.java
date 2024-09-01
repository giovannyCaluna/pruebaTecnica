package com.bank.cliente.services;


import com.bank.cliente.DTOs.*;

import com.bank.cliente.data.ClienteRespository;
import com.bank.cliente.exceptions.ClienteNotFoundException;
import com.bank.cliente.exceptions.ServicioNotAvailable;
import com.bank.cliente.models.Cliente;
import com.bank.cliente.models.Persona;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.bank.cliente.config.RabbitConfig.EXCHANGE_NAME;
import static com.bank.cliente.config.RabbitConfig.M2_ROUTING;

@Service
public class ClienteService {

    private final ClienteRespository clienteRespository;
    private final PersonaService personaService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ClienteService(ClienteRespository clienteRepository, PersonaService personaService, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.clienteRespository = clienteRepository;
        this.personaService = personaService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Cliente> getClientes() throws JsonProcessingException {
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
        RequestCuentaDTO requestCuentaDTO = new RequestCuentaDTO(clientId, startDate, endDate);
        Optional<Cliente> cliente = clienteRespository.findById(clientId);
        if (cliente.isPresent()) {
            Object cuentasResponse = rabbitTemplate.
                    convertSendAndReceive(EXCHANGE_NAME, M2_ROUTING, objectMapper.writeValueAsString(requestCuentaDTO));
            if (cuentasResponse == null) {
                throw new ServicioNotAvailable("El servicio de cuentas no esta disponible");

            }else{
                List<CuentaDTO> cuentas = objectMapper.readValue(cuentasResponse.toString(), new TypeReference<List<CuentaDTO>>() {
                });
                report.setClienteid(cliente.get().getClienteId());
                report.setCliente(cliente.get().getPersona().getNombre());
                report.setFechaReporte(new Date());
                List<CuentaReporte> cuentasReporte = getCuentaReportes(cuentas);
                report.setCuentas(cuentasReporte);
            }
        } else {
            throw new ClienteNotFoundException("El cliente no existe");
        }
        return report;
    }

    private static List<CuentaReporte> getCuentaReportes(List<CuentaDTO> cuentas) {
        List<CuentaReporte> cuentasReporte = new ArrayList<>();
        if (!cuentas.isEmpty()) {
            for (CuentaDTO cuenta : cuentas) {
                CuentaReporte cuentaReporte = new CuentaReporte();
                cuentaReporte.setNumeroCuenta(cuenta.getNumeroCuenta());
                cuentaReporte.setTypo(cuenta.getTipoCuenta());
                cuentaReporte.setState(cuenta.getEstado());
                List<ReportLine> reportLines = new ArrayList<>();
                List<MovimientoDTO> movimientos = cuenta.getMovimientos();
                if (!movimientos.isEmpty()) {
                    for (MovimientoDTO movimiento : movimientos) {
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
        return cuentasReporte;
    }
}
