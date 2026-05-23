package com.app_with_database;

import com.app_with_database.dto.RegistrationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Теперь:
 *  - @TestInstance(PER_CLASS) чтобы @BeforeAll мог быть нестатическим
 *  - В @BeforeAll регистрируем и логиним один раз
 *  - Тесты просто проверяют статус и сессию
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthIntegrationTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;

    private MockHttpSession session;

    @BeforeAll
    void setUp() throws Exception {
        // 1) Регистрация
        RegistrationDTO dto = new RegistrationDTO("testuser","pass123","Test User");
        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        // 2) Логин (формой) и сохраняем сессию
        session = (MockHttpSession) mvc.perform(formLogin("/api/auth/login")
                        .user("testuser")
                        .password("pass123"))
                .andExpect(status().isOk())
                .andReturn()
                .getRequest()
                .getSession(false);

        assertThat(session).isNotNull();
    }

    @Test
    void loginShouldCreateSession() {
        // Просто убеждаемся, что сессия есть
        assertThat(session).isNotNull();
    }

    @Test
    void authenticatedRequestShouldSucceed() throws Exception {
        mvc.perform(get("/api/cats?color=any&page=0&size=1")
                        .session(session))
                .andExpect(status().isOk());
    }

}
