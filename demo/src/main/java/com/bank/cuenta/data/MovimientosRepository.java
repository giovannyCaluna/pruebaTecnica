package com.bank.cuenta.data;

import com.bank.cuenta.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MovimientosRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuenta_NumeroCuentaOrderByFechaDesc(String cuentaId);

    Movimiento findFirstByCuenta_NumeroCuentaOrderByFechaDesc(String cuentaId);

    List<Movimiento> findByCuenta_NumeroCuentaAndAndFechaBetweenOrderByFechaDesc(String cuentaId, Date start, Date end);

}
