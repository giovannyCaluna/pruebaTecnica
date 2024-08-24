package com.bank.demo.services;


import com.bank.demo.data.PersonaRepository;
import com.bank.demo.models.Persona;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonaServiceTests {

    @Autowired
    private PersonaService personaService;

    @MockBean
    private PersonaRepository personaRepository;

    @Test
    public void testSavePersona() throws Exception {
        // Arrange
        Persona persona = new Persona("123456789", "John Doe", "Male", 30, "1234 Elm St", "555-1234");
        when(personaRepository.save(persona)).thenReturn(persona);
        // Act
        Persona savedPersona = personaService.savePersona(persona);
        // Assert
        assertThat(savedPersona).isEqualTo(persona);  // Check if the returned persona is the same as the input
        verify(personaRepository).save(persona);  // Verify if the save method was called on the repository
    }




}




