package com.bank.cuenta.DTOs;

import lombok.Data;

import java.util.Date;
@Data
public class RequestMovimientosDTO {
    private String numeroCuenta;
    private Date fechaInicio;
    private Date fechaFin;

}
