package com.app_with_database.controller;

import com.app_with_database.model.Pet;
import com.app_with_database.model.Color;
import com.app_with_database.service.PetService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PetController {
    private PetService service;

    public PetController() {
        this.service = new PetService();
    }

    // Пакетный конструктор для тестов (Mockito подставит mock)
    PetController(PetService service) {
        this.service = service;
    }

    public void createPet() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Имя: ");
        String name = sc.nextLine();
        System.out.print("Дата рождения (YYYY-MM-DD): ");
        LocalDate bd = LocalDate.parse(sc.nextLine());
        System.out.print("Порода: ");
        String breed = sc.nextLine();
        System.out.print("Цвет (BLACK/WHITE/...): ");
        Color color = Color.valueOf(sc.nextLine().toUpperCase());

        Pet p = new Pet();
        p.setName(name);
        p.setBirthDate(bd);
        p.setBreed(breed);
        p.setColor(color);
        service.create(p);
        System.out.println("Сохранено с ID=" + p.getId());
    }

    public void listPets() {
        List<Pet> all = service.getAll();
        all.forEach(p -> System.out.printf("%d: %s%n", p.getId(), p.getName()));
    }

    public void getPetById() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите ID питомца: ");
        long id = Long.parseLong(sc.nextLine());
        Pet p = service.getById(id);
        if (p != null) {
            System.out.printf("ID=%d, Имя=%s, ДР=%s, Порода=%s, Цвет=%s%n",
                    p.getId(), p.getName(), p.getBirthDate(),
                    p.getBreed(), p.getColor());
        } else {
            System.out.println("Питомец не найден.");
        }
    }

    public void updatePet() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите ID для обновления: ");
        long id = Long.parseLong(sc.nextLine());
        Pet p = service.getById(id);
        if (p == null) {
            System.out.println("Питомец не найден.");
            return;
        }
        System.out.print("Новое имя (текущее: " + p.getName() + "): ");
        String name = sc.nextLine();
        System.out.print("Новая ДР (YYYY-MM-DD, текущая: " + p.getBirthDate() + "): ");
        String bd = sc.nextLine();
        System.out.print("Новая порода (текущая: " + p.getBreed() + "): ");
        String breed = sc.nextLine();
        System.out.print("Новый цвет (текущий: " + p.getColor() + "): ");
        String colorStr = sc.nextLine();

        if (!name.isBlank()) p.setName(name);
        if (!bd.isBlank())   p.setBirthDate(LocalDate.parse(bd));
        if (!breed.isBlank())p.setBreed(breed);
        if (!colorStr.isBlank())
            p.setColor(Color.valueOf(colorStr.toUpperCase()));

        service.update(p);
        System.out.println("Питомец обновлён.");
    }

    public void deletePet() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите ID для удаления: ");
        long id = Long.parseLong(sc.nextLine());
        service.deleteById(id);
        System.out.println("Питомец с ID=" + id + " удалён.");
    }
}
