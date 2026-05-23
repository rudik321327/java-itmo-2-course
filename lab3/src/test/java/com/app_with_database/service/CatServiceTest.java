package com.app_with_database.service;

import com.app_with_database.dto.CatDTO;
import com.app_with_database.model.Cat;
import com.app_with_database.repository.CatRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CatServiceTest {

    @MockBean
    private CatRepository catRepository;

    @Test
    void testGetCat() {
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setName("Whiskers");
        cat.setColor("Orange");

        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cat));

        CatService catService = new CatService(catRepository);

        CatDTO dto = catService.getCat(1L);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Whiskers");
        assertThat(dto.getColor()).isEqualTo("Orange");
    }

    @Test
    void testGetCatsByColor() {
        Cat cat1 = new Cat();
        cat1.setId(1L);
        cat1.setName("Whiskers");
        cat1.setColor("Orange");

        List<Cat> cats = List.of(cat1);

        Mockito.when(catRepository.findByColor(Mockito.anyString(), Mockito.any())).thenReturn(cats);

        CatService catService = new CatService(catRepository);

        List<CatDTO> dtos = catService.getCatsByColor("Orange", 0, 5);

        assertThat(dtos).hasSize(1);
        assertThat(dtos.get(0).getName()).isEqualTo("Whiskers");
    }
}
