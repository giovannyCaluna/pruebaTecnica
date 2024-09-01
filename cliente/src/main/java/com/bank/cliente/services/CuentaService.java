package com.bank.cliente.services;

import com.bank.cliente.DTOs.CuentaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CuentaService {
    private final RestTemplate restTemplate;
    @Value("${cuenta.api.url}")
    private String urlApiCuenta;


    public CuentaService() {
        this.restTemplate = new RestTemplate();
    }

    public Object getAllCuentas() {
        ResponseEntity<?> response = restTemplate.getForEntity(urlApiCuenta, List.class);
        return response.getBody();
    }

    public Object createCuenta(CuentaDTO cuenta) throws Exception {
        ResponseEntity<?> response = restTemplate.postForEntity(urlApiCuenta, cuenta, Object.class);
        return response.getBody();
    }

    public String updateCuenta(CuentaDTO cuenta, String cuentaid) throws Exception {
        String m1Url = urlApiCuenta + "/" + cuentaid;
        restTemplate.put(m1Url, cuenta);
        return "Cuenta actualizada";
    }

    public String deleteCuenta(String cuentaid) throws Exception {
        String m1Url = urlApiCuenta + "/" + cuentaid;
        restTemplate.delete(m1Url);
        return "Cuenta Eliminada";
    }


}
