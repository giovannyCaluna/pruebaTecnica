package com.bank.cliente.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class RequestCuentaDTO {
    @NotNull(message = "El clienteid no puede ser nulo")
    private String clienteid;
    private Date fechaInicio;
    private Date fechaFin;

    public RequestCuentaDTO(String clienteid, Date fechaInicio, Date fechaFin) {
        this.clienteid = clienteid;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;

    }
}
