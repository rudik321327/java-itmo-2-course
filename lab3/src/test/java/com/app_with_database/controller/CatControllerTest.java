package com.app_with_database.controller;

import com.app_with_database.dto.CatDTO;
import com.app_with_database.service.CatService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatController.class)
public class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;

    @BeforeEach
    void setup() {
        Mockito.when(catService.getCat(anyLong()))
                .thenReturn(new CatDTO(1L, "Whiskers", "Orange"));

        Mockito.when(catService.getCatsByColor(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(new CatDTO(1L, "Whiskers", "Orange")));
    }

    @Test
    void testGetCatById() throws Exception {
        mockMvc.perform(get("/api/cats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Whiskers"))
                .andExpect(jsonPath("$.color").value("Orange"));
    }

    @Test
    void testGetCatsByColor() throws Exception {
        mockMvc.perform(get("/api/cats")
                        .param("color", "Orange")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Whiskers"))
                .andExpect(jsonPath("$[0].color").value("Orange"));
    }
}
