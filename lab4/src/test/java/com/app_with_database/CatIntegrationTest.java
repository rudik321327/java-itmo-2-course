package com.app_with_database;

import com.app_with_database.dto.CatDTO;
import com.app_with_database.dto.RegistrationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты работы с котами. Пользователи регистрируются и логинятся единожды,
 * сессии переиспользуются в каждом тесте.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CatIntegrationTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;

    private MockHttpSession sessionUser1;
    private MockHttpSession sessionUser2;

    @BeforeAll
    void setUp() throws Exception {
        // Регистрируем и логиним user1
        RegistrationDTO dto1 = new RegistrationDTO("user1","pwd1","User One");
        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto1)))
                .andExpect(status().isCreated());
        sessionUser1 = (MockHttpSession) mvc.perform(formLogin("/api/auth/login")
                        .user("user1").password("pwd1"))
                .andExpect(status().isOk())
                .andReturn().getRequest().getSession(false);

        // Регистрируем и логиним user2
        RegistrationDTO dto2 = new RegistrationDTO("user2","pwd2","User Two");
        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto2)))
                .andExpect(status().isCreated());
        sessionUser2 = (MockHttpSession) mvc.perform(formLogin("/api/auth/login")
                        .user("user2").password("pwd2"))
                .andExpect(status().isOk())
                .andReturn().getRequest().getSession(false);
    }


    @Test
    void createAndGetCat_AsUser1_Should200() throws Exception {
        // создаём кота от user1
        CatDTO newCat = new CatDTO(null,"Whiskers","black");
        var res = mvc.perform(post("/api/cats")
                        .session(sessionUser1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(newCat)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Whiskers"))
                .andReturn();

        Long catId = om.readTree(res.getResponse().getContentAsString())
                .get("id").asLong();

        mvc.perform(get("/api/cats/" + catId)
                        .session(sessionUser1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("black"));
    }

    @Test
    void updateCat_NotOwner_Should403() throws Exception {
        // создаём кота от user1
        CatDTO cat = new CatDTO(null,"Mittens","white");
        var res = mvc.perform(post("/api/cats")
                        .session(sessionUser1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(cat)))
                .andExpect(status().isCreated())
                .andReturn();

        Long catId = om.readTree(res.getResponse().getContentAsString())
                .get("id").asLong();

        // пытаемся обновить чужого кота под user2
        CatDTO updated = new CatDTO(null,"MittensX","white");
        mvc.perform(put("/api/cats/" + catId)
                        .session(sessionUser2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(updated)))
                .andExpect(status().isForbidden());
    }
}
