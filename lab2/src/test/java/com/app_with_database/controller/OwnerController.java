package com.app_with_database.controller;

import com.app_with_database.model.Owner;
import com.app_with_database.service.OwnerService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.io.*;
import static org.mockito.Mockito.*;

class OwnerControllerTest {
    @InjectMocks OwnerController ctrl;
    @Mock      OwnerService service;

    @BeforeEach void init() {
        MockitoAnnotations.openMocks(this);
        System.setIn(new ByteArrayInputStream("Alex\n1990-02-02\n".getBytes()));
    }

    @Test void createOwnerInvokesService() {
        when(service.create(any())).thenReturn(new Owner());
        ctrl.createOwner();
        verify(service).create(any(Owner.class));
    }
}
