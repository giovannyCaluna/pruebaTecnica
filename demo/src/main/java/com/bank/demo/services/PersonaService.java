package com.bank.demo.services;

import com.bank.demo.data.PersonaRepository;
import com.bank.demo.models.Persona;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
    private PersonaRepository personaRepository;
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }
    public Persona savePersona(Persona persona) {
        return personaRepository.save(persona);
    }
}
