package com.bank.demo.models;

import jakarta.persistence.*;


@Entity
@Table(name = "Cliente")
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

    // Getters and Setters

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
