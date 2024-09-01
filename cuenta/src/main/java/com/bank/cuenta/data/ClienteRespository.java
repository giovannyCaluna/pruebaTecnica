package com.bank.cuenta.data;

import com.bank.cuenta.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRespository  extends JpaRepository<Cliente, String> {

}
