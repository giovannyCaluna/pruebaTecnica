package com.bank.demo.controllers;

import com.bank.demo.DTOs.MovimientoDTO;
import com.bank.demo.models.Movimiento;
import com.bank.demo.services.MovimientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/movimiento")
public class MovimientosRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MovimientosRestController.class);

    private MovimientoService movimientoService;

    public MovimientosRestController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMovimientos() {
        try {
            return new ResponseEntity<>(movimientoService.getAllMovimientos(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cuenta/{numeroCuenta}")
    public ResponseEntity<?> getMovimientosByCuenta(@PathVariable String numeroCuenta) {
        try {
            return new ResponseEntity<>(movimientoService.getMovimientosByCuenta(numeroCuenta), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createmovimiento(@RequestBody MovimientoDTO movimiento) throws Exception {
        try {
            return new ResponseEntity<>(movimientoService.createMovimiento(movimiento), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{movimientoid}")
    public ResponseEntity<?> updateMovimiento(@RequestBody MovimientoDTO movimiento, @PathVariable Long movimientoid) throws Exception{
        try {
            return new ResponseEntity<>(movimientoService.updateMovimiento(movimiento, movimientoid), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{movimientoid}")
    public ResponseEntity<?> deleteMovimiento(@PathVariable Long movimientoid) {
        try {
            movimientoService.deleteMovimiento(movimientoid);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch
        (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
