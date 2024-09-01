package com.bank.cuenta.data;

import com.bank.cuenta.models.Accion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccionRepository extends JpaRepository<Accion, Long> {
}
