package com.app_with_database.service;

import com.app_with_database.dto.OwnerDTO;
import com.app_with_database.model.Cat;
import com.app_with_database.model.Owner;
import com.app_with_database.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OwnerServiceTest {

    @MockBean
    private OwnerRepository ownerRepository;

    @Test
    void testGetOwner() {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("Alice");

        Cat cat1 = new Cat();
        cat1.setName("Whiskers");
        Cat cat2 = new Cat();
        cat2.setName("Tom");

        owner.setCats(List.of(cat1, cat2));

        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));

        OwnerService ownerService = new OwnerService(ownerRepository);

        OwnerDTO dto = ownerService.getOwner(1L);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Alice");
        assertThat(dto.getCats()).containsExactly("Whiskers", "Tom");
    }
}
