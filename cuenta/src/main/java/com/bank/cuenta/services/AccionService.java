package com.bank.cuenta.services;

import com.bank.cuenta.data.AccionRepository;
import com.bank.cuenta.exceptions.AccionNoCreadaException;
import com.bank.cuenta.models.Accion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccionService  {
    private final AccionRepository accionRepository;
    public AccionService(AccionRepository accionRepository) {
        this.accionRepository = accionRepository;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Accion create(Accion accion) throws AccionNoCreadaException {
        try{
            return accionRepository.save(accion);
        }catch (Exception e){
            throw new AccionNoCreadaException("No se registro la accion");
        }
    }
}
