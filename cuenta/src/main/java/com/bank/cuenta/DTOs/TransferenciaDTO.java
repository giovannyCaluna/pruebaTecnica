package com.bank.cuenta.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TransferenciaDTO {
    @NotNull(message = "El clienteid no puede ser nulo")
    String clienteId;
    @NotNull(message = "La cuentaOrigen no puede ser nulo")
    @Size(min = 6, max = 6, message = "El número de cuenta debe tener exactamente 6 caracteres")
    String cuentaOrigen;
    @Size(min = 6, max = 6, message = "El número de cuenta debe tener exactamente 6 caracteres")
    @NotNull(message = "La cuentaDestino no puede ser nulo")
    String cuentaDestino ;
    @NotNull(message = "El monto no puede ser nulo")
    @Min(value = 0, message = "El monto a debitar no puede ser negativo")
    double monto;
}
