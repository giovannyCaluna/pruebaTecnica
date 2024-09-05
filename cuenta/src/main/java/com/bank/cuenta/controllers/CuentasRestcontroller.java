package com.bank.cuenta.controllers;

import com.bank.cuenta.DTOs.CuentaDTO;
import com.bank.cuenta.DTOs.CuentaUpdateDTO;
import com.bank.cuenta.exceptions.ClienteNotFoundException;
import com.bank.cuenta.services.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cuenta")
public class CuentasRestcontroller {
    private CuentaService cuentaService;

    public CuentasRestcontroller(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public ResponseEntity<?> getAllcuentas() {
        try {
            return new ResponseEntity<>(cuentaService.getAllCuentas(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCuenta(@Valid @RequestBody CuentaDTO cuenta) throws Exception, ClienteNotFoundException {
        try {
            return new ResponseEntity<>(cuentaService.createCuenta(cuenta), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{cuentaid}")
    public ResponseEntity<?> updateCuenta(@Valid @RequestBody CuentaUpdateDTO cuenta, @PathVariable String cuentaid) throws Exception {
        try {
            return new ResponseEntity<>(cuentaService.updateCuenta(cuenta, cuentaid), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping("/{cuentaid}")
    public ResponseEntity<?> deleteCuenta(@PathVariable String cuentaid) throws Exception {
        try {
            cuentaService.deleteCuenta(cuentaid);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<String> handleClienteNotFoundException(ClienteNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }



}
