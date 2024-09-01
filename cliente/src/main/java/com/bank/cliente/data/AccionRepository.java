package com.bank.cliente.data;

import com.bank.cliente.models.Accion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccionRepository extends JpaRepository<Accion, Long> {
}
