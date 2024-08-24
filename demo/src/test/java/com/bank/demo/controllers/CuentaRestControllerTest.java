package com.bank.demo.controllers;

import com.bank.demo.DTOs.CuentaDTO;
import com.bank.demo.data.ClienteRespository;
import com.bank.demo.data.CuentasRepository;
import com.bank.demo.models.Cliente;
import com.bank.demo.models.Cuenta;
import com.bank.demo.models.Movimiento;
import com.bank.demo.services.CuentaService;
import com.bank.demo.services.MovimientoService;

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

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CuentasRestcontroller.class)
public class CuentaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CuentaService cuentaService;

    @MockBean
    private ClienteRespository clienteRespository;

    @MockBean
    private CuentasRepository cuentaRepository;

    @MockBean
    private MovimientoService movimientoService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testCreateCuenta_Success() throws Exception {
        // Arrange
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setClienteId("CL001");
        cuentaDTO.setTipo("Ahorros");
        cuentaDTO.setSaldoInicial(1000.0);
        cuentaDTO.setEstado(true);
        cuentaDTO.setCuentaid("123456");

        Cliente cliente = new Cliente();
        cliente.setClienteId("CL001");

        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setCliente(cliente);
        nuevaCuenta.setNumeroCuenta("123456");
        nuevaCuenta.setTipoCuenta("Deposit");
        nuevaCuenta.setSaldoInicial(1000.0);
        nuevaCuenta.setEstado(true);

        when(clienteRespository.findById(cuentaDTO.getClienteId())).thenReturn(Optional.of(cliente));
        when(cuentaRepository.save(Mockito.any(Cuenta.class))).thenReturn(nuevaCuenta);

        Movimiento primerMovimiento = new Movimiento();
        primerMovimiento.setTipoMovimiento("Deposito");
        primerMovimiento.setCuenta(nuevaCuenta);
        primerMovimiento.setValor(1000.0);
        primerMovimiento.setSaldo(1000.0);
        primerMovimiento.setFecha(new Date());

        when(movimientoService.setPrimerMovimiento(any(Movimiento.class))).thenReturn(primerMovimiento);

        // Act & Assert
        mockMvc.perform(post("/api/cuenta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clienteId\":\"CL001\",\"cuentaid\":\"12346\",\"tipo\":\"Deposito\",\"saldoInicial\":1000.0,\"estado\":true}"))
                .andExpect(status().isCreated());
    }

   // @Test
    public void testCreateCuenta_ClienteNotFound() throws Exception {
        // Arrange
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setClienteId("12");
        cuentaDTO.setCuentaid("12346");
        cuentaDTO.setTipo("Deposito");
        cuentaDTO.setSaldoInicial(1000.0);
        cuentaDTO.setEstado(true);

        String cuentaJson = objectMapper.writeValueAsString(cuentaDTO);

        when(clienteRespository.findById(cuentaDTO.getClienteId())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(post("/api/cuenta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuentaJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cliente no encontrado"));

    }
}
