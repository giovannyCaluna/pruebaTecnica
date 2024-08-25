package com.bank.demo.integration.controllers;

import com.bank.demo.models.Cliente;
import com.bank.demo.models.Persona;
import com.bank.demo.data.ClienteRespository;
import com.bank.demo.data.PersonaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRespository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    private Persona persona;
    private Cliente cliente;

    @BeforeEach
    public void setup() {
        // Set up a Persona and Cliente before each test
        persona = new Persona();
        persona.setIdentificacion("123");
        persona.setNombre("Usuario Test");
        persona.setGenero("Hombre");
        persona.setEdad(25);
        persona.setDireccion("Quito Ecuador");
        persona.setTelefono("555-1234");
        personaRepository.save(persona);

        cliente = new Cliente();
        cliente.setClienteId("CL100");
        cliente.setContrasena("123456");
        cliente.setEstado(true);
        cliente.setPersona(persona);
        clienteRepository.save(cliente);
    }

    @Test
    public void testCreateClient() throws Exception {
        Persona personaTest =  new Persona();
        personaTest.setIdentificacion("1234567890");
        personaTest.setNombre("Usuario Test");
        personaTest.setGenero("Hombre");
        personaTest.setEdad(25);
        personaTest.setDireccion("Quito Ecuador");
        Cliente clienteTest =  new Cliente();
        clienteTest.setClienteId("CL200");
        clienteTest.setContrasena("123456");
        clienteTest.setEstado(true);
        clienteTest.setPersona(personaTest);
        mockMvc.perform(post("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteTest)))
                .andExpect(status().isCreated());

        assertThat(clienteRepository.findById("CL100")).isPresent();
    }
    @Test
    public void testFailCreateClient() throws Exception {
        Persona personaTest =  new Persona();
        personaTest.setIdentificacion("123");
        personaTest.setNombre("Usuario Test");
        personaTest.setGenero("Hombre");
        personaTest.setEdad(25);
        personaTest.setDireccion("Quito Ecuador");
        Cliente clienteTest =  new Cliente();
        clienteTest.setClienteId("CL100");
        clienteTest.setContrasena("123456");
        clienteTest.setEstado(true);
        clienteTest.setPersona(personaTest);
        mockMvc.perform(post("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteTest)))
                .andExpect(status().isBadRequest());
        assertThat(clienteRepository.findById("CL100")).isPresent();
        assertThat(personaRepository.findById("123")).isPresent();
    }

    @Test
    public void testGetAllClients() throws Exception {
        mockMvc.perform(get("/api/cliente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].clienteId").value("CL001"));
    }

    @Test
    public void testUpdateClient() throws Exception {
        String clienteId = "CL100";
        Persona personaTest =  new Persona();
        personaTest.setIdentificacion("123");
        personaTest.setNombre("Usuario Test");
        personaTest.setGenero("Hombre");
        personaTest.setEdad(31);
        personaTest.setDireccion("Quito Ecuador");
        personaTest.setTelefono("555-1234");
        Cliente clienteTest =  new Cliente();
        clienteTest.setClienteId(clienteId);
        clienteTest.setContrasena("55555");
        clienteTest.setEstado(true);
        clienteTest.setPersona(personaTest);




        mockMvc.perform(put("/api/cliente/{clienteId}", clienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteTest)))
                .andExpect(status().isOk());

        // Verify that the client was updated in the repository
        Cliente updatedCliente = clienteRepository.findById("CL100").orElseThrow();
        assertThat(updatedCliente.getContrasena()).isEqualTo("55555");
        assertThat(updatedCliente.getPersona().getEdad()).isEqualTo(31);
    }

    @Test
    public void testDeleteClient() throws Exception {
        String clienteId = "C123";
        mockMvc.perform(delete("/api/cliente/{clienteid}",clienteId ))
                .andExpect(status().isOk());

        // Verify that the client was deleted
        assertThat(clienteRepository.findById("C123")).isNotPresent();
    }

    @Test
    public void testGetReportes() throws Exception {
        String clienteId = "CL100";
        String url = "/api/cliente/{clienteid}/reporte?fecha=" + LocalDate.now().minusDays(10) + "," + LocalDate.now();
        MvcResult result = mockMvc.perform(get(url, clienteId))
                .andExpect(status().isOk())
                .andReturn();

        // Further assertions can be done based on the content of the report
        assertThat(result.getResponse().getContentAsString()).isNotEmpty();
    }
}
