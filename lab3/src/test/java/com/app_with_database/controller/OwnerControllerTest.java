package com.app_with_database.controller;

import com.app_with_database.dto.OwnerDTO;
import com.app_with_database.service.OwnerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @BeforeEach
    void setup() {
        Mockito.when(ownerService.getOwner(anyLong()))
                .thenReturn(new OwnerDTO(1L, "Alice", List.of("Whiskers", "Tom")));
    }

    @Test
    void testGetOwnerById() throws Exception {
        mockMvc.perform(get("/api/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.cats[0]").value("Whiskers"))
                .andExpect(jsonPath("$.cats[1]").value("Tom"));
    }
}
