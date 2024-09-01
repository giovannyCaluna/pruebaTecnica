package com.bank.cuenta.async;


import com.bank.cuenta.DTOs.CuentaDTO;
import com.bank.cuenta.DTOs.MovimientoDTO;
import com.bank.cuenta.DTOs.RequestCuentaDTO;
import com.bank.cuenta.models.Cuenta;
import com.bank.cuenta.models.Movimiento;
import com.bank.cuenta.services.CuentaService;
import com.bank.cuenta.services.MovimientoService;
import com.bank.cuenta.utils.DtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestCuentaListener {
    private static final Logger LOG = LoggerFactory.getLogger(RequestCuentaListener.class);

    private final ObjectMapper mapper;
    private final CuentaService cuentaService;
    private final MovimientoService movimientoService;


    public RequestCuentaListener(CuentaService cuentaService, MovimientoService movimientoService, ObjectMapper mapper) {
        this.cuentaService = cuentaService;
        this.movimientoService = movimientoService;
        this.mapper = mapper;
    }

    public String handleRequestCuentas(String mensaje) {
        try {

            RequestCuentaDTO requestCuentaDTO = mapper.readValue(mensaje, RequestCuentaDTO.class);
            List<Cuenta> cuentas = cuentaService.findBycliente(requestCuentaDTO.getClienteid());

            // Inicializa la lista de CuentaDTOs
            List<CuentaDTO> cuentaDTOs = cuentas.stream().map(cuenta -> {
                List<Movimiento> movimientos = movimientoService.findByCuentaAndIntervalo(
                        cuenta.getNumeroCuenta(),
                        requestCuentaDTO.getFechaInicio(),
                        requestCuentaDTO.getFechaFin()
                );
                // Convierte los movimientos a MovimientoDTOs
                List<MovimientoDTO> movimientosDto = movimientos.stream()
                        .map(DtoConverter::convertToMovimientoDto)
                        .collect(Collectors.toList());
                // Convierte la cuenta a CuentaDTO y asocia los movimientosDTO
                return DtoConverter.convertToCuentaDto(cuenta, movimientosDto);
            }).collect(Collectors.toList());
            LOG.info(mapper.writeValueAsString(cuentaDTOs));
            return mapper.writeValueAsString(cuentaDTOs);
        } catch (Exception e) {
            LOG.warn("Mensaje incorrecto", e);
            return null;
        }

    }

}
