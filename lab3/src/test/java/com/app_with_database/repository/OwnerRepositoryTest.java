package com.app_with_database.repository;

import com.app_with_database.model.Cat;
import com.app_with_database.model.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void testOwnerCatsRelationship() {
        Owner owner = new Owner();
        owner.setName("Alice");

        Cat cat1 = new Cat();
        cat1.setName("Whiskers");
        cat1.setColor("Orange");
        cat1.setOwner(owner);

        Cat cat2 = new Cat();
        cat2.setName("Tom");
        cat2.setColor("Black");
        cat2.setOwner(owner);

        owner.setCats(List.of(cat1, cat2));

        ownerRepository.save(owner);

        Owner savedOwner = ownerRepository.findById(owner.getId()).orElseThrow();

        assertThat(savedOwner.getCats()).hasSize(2);
        assertThat(savedOwner.getCats()).extracting("name").containsExactlyInAnyOrder("Whiskers", "Tom");
    }
}
