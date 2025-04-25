package com.app_with_database.service;

import com.app_with_database.dao.OwnerDao;
import com.app_with_database.model.Owner;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerServiceTest {
    @InjectMocks OwnerService service;
    @Mock      OwnerDao dao;

    @BeforeEach void init() { MockitoAnnotations.openMocks(this); }

    @Test void createValid() {
        Owner o = new Owner(); o.setName("Ivan"); o.setBirthDate(LocalDate.of(1980,1,1));
        when(dao.save(o)).thenReturn(o);
        assertEquals("Ivan", service.create(o).getName());
    }

    @Test void createNoDate() {
        Owner o = new Owner(); o.setName("Petr");
        assertThrows(IllegalArgumentException.class, () -> service.create(o));
    }

}
