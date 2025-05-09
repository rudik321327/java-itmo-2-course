package com.app_with_database.controller;

import com.app_with_database.model.Owner;
import com.app_with_database.service.OwnerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    private OwnerController ctrl;

    @Mock
    private OwnerService service;

    @BeforeEach
    void setUp() {

        System.setIn(new ByteArrayInputStream("Alex\n1990-02-02\n".getBytes()));
    }

    @Test
    void createOwnerInvokesService() {
        when(service.create(any(Owner.class))).thenReturn(new Owner());

        ctrl.createOwner();

        verify(service).create(any(Owner.class));
    }
}
