package com.bank.demo.services;

import com.bank.demo.data.PersonaRepository;
import com.bank.demo.models.Persona;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonaService {
    private PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Persona savePersona(Persona persona) throws Exception {
        Optional<Persona> personaRegistrada = this.personaRepository.findById(persona.getIdentificacion());
        if (personaRegistrada.isPresent()) {
            throw new Exception("Usuario registrado");
        } else {
            return personaRepository.save(persona);
        }
    }

    public Persona updatePersona(Persona persona) throws Exception {
        Optional<Persona> personaRegistrada = this.personaRepository.findById(persona.getIdentificacion());
        if (!personaRegistrada.isPresent()) {
            throw new Exception("Usuario NO registrado");
        } else {
            return personaRepository.save(persona);
        }
    }
    public Optional<Persona> findByIdentificacion(String identificacion) {
        return this.personaRepository.findById(identificacion);
    }
}
