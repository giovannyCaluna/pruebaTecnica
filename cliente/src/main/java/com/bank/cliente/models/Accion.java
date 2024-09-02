package com.bank.cliente.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Table(name = "Acciones")
@Data
public class Accion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "clienteId", nullable = false)
    private String clienteId;

    @Column(name = "descripcion", nullable = false)
    private String tipoMovimiento;

}
