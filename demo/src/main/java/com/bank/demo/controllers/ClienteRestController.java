package com.bank.demo.controllers;

import com.bank.demo.models.Cliente;
import com.bank.demo.services.ClienteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteRestController {

    private ClienteService clienteService;

    public ClienteRestController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClients() {
        return new ResponseEntity<>(clienteService.getClientes(), HttpStatus.OK);
    }

    @GetMapping("/{clienteid}/reporte")
    public ResponseEntity<?> getReportes(@PathVariable String clienteid,
                                         @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate[] fecha) throws Exception {

        if (fecha.length != 2) {
            throw new IllegalArgumentException("The fecha parameter must contain a start and end date");
        }

        return new ResponseEntity<>(clienteService.generateReport(clienteid,fecha), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Cliente> createClient(@RequestBody Cliente cliente) {
        try {
            return new ResponseEntity<>(clienteService.createClient(cliente), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody Cliente cliente) {
        try {
            return new ResponseEntity<>(clienteService.updateClient(cliente), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteClient(@RequestBody Cliente cliente) {
        try {
            this.clienteService.deleteClient(cliente);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
