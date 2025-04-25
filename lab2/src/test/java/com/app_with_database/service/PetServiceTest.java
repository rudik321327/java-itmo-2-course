package com.app_with_database.service;

import com.app_with_database.dao.PetDao;
import com.app_with_database.model.Pet;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceTest {
    @InjectMocks PetService service;
    @Mock      PetDao dao;

    @BeforeEach void init() { MockitoAnnotations.openMocks(this); }


    @Test void createNoName() {
        Pet p = new Pet(); p.setBirthDate(LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> service.create(p));
    }

    @Test void updateNoId() {
        Pet p = new Pet(); p.setName("Test"); p.setBirthDate(LocalDate.now());
        assertThrows(NullPointerException.class, () -> service.update(p));
    }

}
