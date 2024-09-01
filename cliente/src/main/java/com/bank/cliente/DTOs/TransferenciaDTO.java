package com.bank.cliente.DTOs;

import lombok.Data;

@Data
public class TransferenciaDTO {
    String clienteId;
    String cuentaOrigen;
    String cuentaDestino ;
    double monto;

}
