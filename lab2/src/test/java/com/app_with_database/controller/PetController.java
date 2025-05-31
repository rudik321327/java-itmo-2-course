package com.app_with_database.controller;

import com.app_with_database.model.Pet;
import com.app_with_database.service.PetService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.io.*;
import static org.mockito.Mockito.*;

class PetControllerTest {
    @InjectMocks PetController ctrl;
    @Mock      PetService service;

    @BeforeEach void init() {
        MockitoAnnotations.openMocks(this);
        String input = "Bars\n2020-01-01\nBreed\nBLACK\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @Test void createPetInvokesService() {
        when(service.create(any())).thenReturn(new Pet());
        ctrl.createPet();
        verify(service).create(any(Pet.class));
    }
}
