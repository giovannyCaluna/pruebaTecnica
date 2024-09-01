package com.bank.cliente.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class RequestCuentaDTO {

    private String clienteid;
    private Date fechaInicio;
    private Date fechaFin;

    public RequestCuentaDTO(String clienteid, Date fechaInicio, Date fechaFin) {
        this.clienteid = clienteid;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;

    }
}
