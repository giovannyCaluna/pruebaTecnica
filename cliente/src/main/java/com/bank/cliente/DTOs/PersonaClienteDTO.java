package com.bank.cliente.DTOs;

import lombok.Data;

@Data
public class PersonaClienteDTO {
    private String identificacion;
    private String clienteId;
    private String contrasena;

    // Constructor
    public PersonaClienteDTO(String identificacion, String clienteId, String contrasena) {
        this.identificacion = identificacion;
        this.clienteId = clienteId;
        this.contrasena = contrasena;
    }


}
