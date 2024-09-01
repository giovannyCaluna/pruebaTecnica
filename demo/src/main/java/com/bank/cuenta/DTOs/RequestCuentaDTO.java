package com.bank.cuenta.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class RequestCuentaDTO {
private String clienteid;
private Date fechaInicio;
private Date fechaFin;
}
