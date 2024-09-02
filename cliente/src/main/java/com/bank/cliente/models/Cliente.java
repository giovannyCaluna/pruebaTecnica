package com.bank.cliente.models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "Cliente")
@Data
public class Cliente {

    @Id
    @Column(name = "clienteid", nullable = false, unique = true)
    private String clienteId;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "estado")
    private Boolean estado;

    @OneToOne
    @JoinColumn(name = "identificacion", referencedColumnName = "identificacion", nullable = false)
    private Persona persona;


    public Cliente() {
    }

    public Cliente(String clienteId, String contrasena, Boolean estado, Persona persona) {
        this.clienteId = clienteId;
        this.contrasena = contrasena;
        this.estado = estado;
        this.persona = persona;
    }

}
