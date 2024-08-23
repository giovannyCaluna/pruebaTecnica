package com.bank.demo.controllers;

import com.bank.demo.DTOs.CuentaDTO;
import com.bank.demo.models.Cliente;
import com.bank.demo.models.Cuenta;
import com.bank.demo.services.ClienteService;
import com.bank.demo.services.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuenta")
public class CuentasRestcontroller {
    private CuentaService cuentaService;

    public CuentasRestcontroller (CuentaService cuentaService){
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public ResponseEntity<?> getAllcuentas (){
        try{
            return new ResponseEntity<>(cuentaService.getAllCuentas(), HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCuenta(@RequestBody CuentaDTO cuenta) throws Exception {
        try{
            return new ResponseEntity<>(cuentaService.createCuenta(cuenta), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCuenta(@RequestBody CuentaDTO cuenta) throws Exception{
        try{
            return new ResponseEntity<>(cuentaService.updateCuenta(cuenta),HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST );

        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteCuenta(@RequestBody CuentaDTO cuenta) throws Exception {
        try{
            cuentaService.deleteCuenta(cuenta);

            return new ResponseEntity<>(null, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
