package com.bank.demo.data;

import com.bank.demo.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentasRepository extends JpaRepository<Cuenta, String> {
    List<Cuenta> findByCliente_ClienteId(String clienteId);
}
