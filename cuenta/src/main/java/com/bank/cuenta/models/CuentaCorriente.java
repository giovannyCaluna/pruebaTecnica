package com.bank.cuenta.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("CORRIENTE")
@Data
public class CuentaCorriente extends Cuenta {
    @Column(name = "monto_descubierto")
    private Double montoDescubierto;
}
