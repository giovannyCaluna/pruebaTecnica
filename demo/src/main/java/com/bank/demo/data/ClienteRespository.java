package com.bank.demo.data;

import com.bank.demo.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRespository  extends JpaRepository<Cliente, String> {

}
