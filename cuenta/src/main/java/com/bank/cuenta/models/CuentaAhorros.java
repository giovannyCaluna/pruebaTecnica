package com.bank.cuenta.models;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("AHORROS")
@Data
public class CuentaAhorros extends Cuenta {

    @Column(name = "interes")
    private Double interes;
}