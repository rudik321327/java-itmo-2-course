package com.app_with_database.repository;

import com.app_with_database.model.Cat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CatRepositoryTest {

    @Autowired
    private CatRepository catRepository;

    @Test
    void testFindByColor() {
        Cat cat1 = new Cat();
        cat1.setName("Whiskers");
        cat1.setColor("Orange");

        catRepository.save(cat1);

        List<Cat> cats = catRepository.findByColor("Orange", org.springframework.data.domain.PageRequest.of(0, 10));

        assertThat(cats).hasSize(1);
        assertThat(cats.get(0).getName()).isEqualTo("Whiskers");
    }
}
