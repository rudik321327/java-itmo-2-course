package com.app_with_database.service;

import com.app_with_database.dao.PetDao;
import com.app_with_database.model.Pet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    PetService service;

    @Test
    void createNoName() {
        Pet p = new Pet();
        p.setBirthDate(LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> service.create(p));
    }

    @Test
    void updateNoId() {
        Pet p = new Pet();
        p.setName("Test");
        p.setBirthDate(LocalDate.now());
        assertThrows(NullPointerException.class, () -> service.update(p));
    }
}
