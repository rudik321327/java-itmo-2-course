package com.app_with_database.service;

import com.app_with_database.dao.OwnerDao;
import com.app_with_database.model.Owner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OwnerServiceTest {

    @InjectMocks
    OwnerService service;

    @Mock
    OwnerDao dao;

    @Test
    void createValid() {
        Owner o = new Owner();
        o.setName("Ivan");
        o.setBirthDate(LocalDate.of(1980, 1, 1));

        lenient().when(dao.save(any(Owner.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Owner saved = service.create(o);

        assertEquals("Ivan", saved.getName());
    }

    @Test
    void createNoDate() {
        Owner o = new Owner();
        o.setName("Petr");

        assertThrows(IllegalArgumentException.class, () -> service.create(o));
    }
}
