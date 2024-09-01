package com.bank.cuenta.controllers;

import com.bank.cuenta.models.Cliente;
import com.bank.cuenta.services.ClienteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        try {
            if (fecha.length != 2) {
                throw new IllegalArgumentException("The fecha parameter must contain a start and end date");
            }
            return new ResponseEntity<>(clienteService.generateReport(clienteid, fecha), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Cliente cliente) {
        try {
            return new ResponseEntity<>(clienteService.createClient(cliente), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{clienteid}")
    public ResponseEntity<?> updateClient(@RequestBody Cliente cliente, @PathVariable String clienteid) {
        try {
            return new ResponseEntity<>(clienteService.updateClient(cliente, clienteid), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{clienteid}")
    public ResponseEntity<?> deleteClient(@PathVariable String clienteid) {
        try {
            this.clienteService.deleteClient(clienteid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
