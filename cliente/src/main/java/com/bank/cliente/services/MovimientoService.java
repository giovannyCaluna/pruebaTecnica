package com.bank.cliente.services;

import com.bank.cliente.DTOs.CrearMovimientoDTO;
import com.bank.cliente.DTOs.TransferenciaDTO;
import com.bank.cliente.DTOs.UpdateMovimientoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovimientoService {

    private final RestTemplate restTemplate;
    @Value("${movimiento.api.url}")
    private String urlApiCuenta;
    public MovimientoService() {
        this.restTemplate = new RestTemplate();
    }

    public Object getAllMovimientos() {
        ResponseEntity<?> response = restTemplate.getForEntity(urlApiCuenta, List.class);
        return response.getBody();
    }

    public Object createMovimiento(CrearMovimientoDTO movimiento) {
        ResponseEntity<?> response = restTemplate.postForEntity(urlApiCuenta, movimiento, Object.class);
        return response.getBody();

    }

    public String updateMovimiento(UpdateMovimientoDTO movimiento, Long movimientoId) throws Exception  {
        String m1Url = urlApiCuenta + "/" + movimientoId;
        restTemplate.put(m1Url, movimiento);
        return "Movimiento actualizado";
    }

    public String deleteMovimiento(Long movimiento) {
        String m1Url = urlApiCuenta + "/" + movimiento;
        restTemplate.delete(m1Url);
        return "Movimiento eliminado";
    }

    public Object getMovimientosByCuenta(String movimientoId) {
        ResponseEntity<?> response = restTemplate.getForEntity(urlApiCuenta + "/cuenta/" + movimientoId, List.class);
        return response.getBody();
    }

    public Object realizarTransferencia(TransferenciaDTO transferenciaDTO) {
        ResponseEntity<?> response = restTemplate.postForEntity(urlApiCuenta +"/transferir", transferenciaDTO, List.class);
        return response.getBody();
    }



}
