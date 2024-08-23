package com.bank.demo.controllers;

import com.bank.demo.DTOs.Report;
import com.bank.demo.models.Cliente;
import com.bank.demo.models.Persona;
import com.bank.demo.services.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteRestController.class)
public class ClienteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private ClienteService clienteService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetAllClients() throws Exception {
        // Arrange
        Persona persona1 = new Persona("123456789", "John Doe", "Male", 30, "1234 Elm St", "555-1234");
        Persona persona2 = new Persona("987654321", "Jane Doe", "Female", 28, "5678 Oak St", "555-5678");
        Cliente cliente1 = new Cliente("1", "password1", true, persona1);
        Cliente cliente2 = new Cliente("2", "password2", false, persona2);
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(clienteService.getClientes()).thenReturn(clientes);

        // Act & Assert
        mockMvc.perform(get("/api/cliente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clienteId").value(cliente1.getClienteId()))
                .andExpect(jsonPath("$[0].persona.nombre").value(persona1.getNombre()))
                .andExpect(jsonPath("$[1].clienteId").value(cliente2.getClienteId()))
                .andExpect(jsonPath("$[1].persona.nombre").value(persona2.getNombre()));
    }

    @Test
    public void testCreateClient() throws Exception {
        // Arrange
        Persona persona = new Persona("123456789", "John Doe", "Male", 30, "1234 Elm St", "555-1234");
        Cliente cliente = new Cliente("1", "password1", true, persona);

        when(clienteService.createClient(Mockito.any(Cliente.class))).thenReturn(cliente);

        // Act & Assert
        mockMvc.perform(post("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clienteId\":\"1\",\"contrasena\":\"password1\",\"estado\":true," +
                                "\"persona\":{\"identificacion\":\"123456789\",\"nombre\":\"John Doe\",\"genero\":\"Male\"," +
                                "\"edad\":30,\"direccion\":\"1234 Elm St\",\"telefono\":\"555-1234\"}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteId").value(cliente.getClienteId()))
                .andExpect(jsonPath("$.persona.nombre").value(persona.getNombre()));
    }

    @Test
    public void testUpdateClient() throws Exception {
        // Arrange
        Persona persona = new Persona("123456789", "John Doee", "Male", 30, "1234 Elm St", "555-1234");
        Cliente cliente = new Cliente("1", "password1", true, persona);

        when(clienteService.updateClient(Mockito.any(Cliente.class))).thenReturn(cliente);

        // Act & Assert
        mockMvc.perform(put("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clienteId\":\"1\",\"contrasena\":\"password1\",\"estado\":true," +
                                "\"persona\":{\"identificacion\":\"123456789\",\"nombre\":\"John Doee\",\"genero\":\"Male\"," +
                                "\"edad\":30,\"direccion\":\"1234 Elm St\",\"telefono\":\"555-1234\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(cliente.getClienteId()))
                .andExpect(jsonPath("$.persona.nombre").value("John Doee"));
    }


    @Test
    public void testGetReportes() throws Exception {
        // Arrange
        String clienteId = "1"; //clienteid sin cuentas
        LocalDate [] dates = { LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31)};
        Report report = new Report();
        report.setCliente(clienteId);
        report.setFechaReporte(new Date());
        report.setCuentas(new ArrayList<>());
        when(clienteService.generateReport(clienteId, dates)).thenReturn(report);

        // Act & Assert
        Timestamp startDate = Timestamp.valueOf(dates[0].atStartOfDay().plusDays(1));

        mockMvc.perform(get("/api/cliente/{clienteid}/reporte", clienteId)
                        .param("fecha", dates[0].toString(), dates[1].toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(report)));
    }
}
