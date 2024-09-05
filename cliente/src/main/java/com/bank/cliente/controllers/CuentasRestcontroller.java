package com.bank.cliente.controllers;

import com.bank.cliente.DTOs.CuentaDTO;
import com.bank.cliente.DTOs.CuentaUpdateDTO;
import com.bank.cliente.services.CuentaService;
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
    public ResponseEntity<?> createCuenta(@Valid @RequestBody CuentaDTO cuenta) {
        try {
            return new ResponseEntity<>(cuentaService.createCuenta(cuenta), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{cuentaid}")
    public ResponseEntity<?> updateCuenta(@Valid @RequestBody CuentaUpdateDTO cuenta, @PathVariable String cuentaid) {
        try {
            return new ResponseEntity<>(cuentaService.updateCuenta(cuenta, cuentaid), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping("/{cuentaid}")
    public ResponseEntity<?> deleteCuenta(@PathVariable String cuentaid) throws Exception {
        try {
            return new ResponseEntity<>(cuentaService.deleteCuenta(cuentaid), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
