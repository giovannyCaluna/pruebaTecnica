package com.bank.cliente.data;

import com.bank.cliente.DTOs.PersonaClienteDTO;
import com.bank.cliente.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRespository  extends JpaRepository<Cliente, String> {
    @Query("SELECT new com.bank.cliente.DTOs.PersonaClienteDTO(p.identificacion, c.clienteId, c.contrasena) \n" +
            "FROM Cliente c \n" +
            "JOIN c.persona p")
    List<PersonaClienteDTO> findPersonaClienteDetails();
}
