package com.bank.cliente.data;

import com.bank.cliente.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRespository  extends JpaRepository<Cliente, String> {

}
