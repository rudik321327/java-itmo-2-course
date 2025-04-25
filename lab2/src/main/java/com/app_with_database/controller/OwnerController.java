package com.app_with_database.controller;

import com.app_with_database.model.Owner;
import com.app_with_database.service.OwnerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class OwnerController {
    private OwnerService service;

    public OwnerController() {
        this.service = new OwnerService();
    }

    // Пакетный конструктор для тестов (Mockito подставит mock)
    OwnerController(OwnerService service) {
        this.service = service;
    }

    public void createOwner() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Имя: ");
        String name = sc.nextLine();
        System.out.print("Дата рождения (YYYY-MM-DD): ");
        LocalDate bd = LocalDate.parse(sc.nextLine());

        Owner o = new Owner();
        o.setName(name);
        o.setBirthDate(bd);
        service.create(o);
        System.out.println("Сохранено с ID=" + o.getId());
    }

    public void listOwners() {
        List<Owner> all = service.getAll();
        all.forEach(o -> System.out.printf("%d: %s%n", o.getId(), o.getName()));
    }

    public void getOwnerById() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите ID владельца: ");
        long id = Long.parseLong(sc.nextLine());
        Owner o = service.getById(id);
        if (o != null) {
            System.out.printf("ID=%d, Имя=%s, ДР=%s, Питомцев=%d%n",
                    o.getId(), o.getName(), o.getBirthDate(),
                    o.getPets() == null ? 0 : o.getPets().size());
        } else {
            System.out.println("Владелец не найден.");
        }
    }

    public void updateOwner() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите ID для обновления: ");
        long id = Long.parseLong(sc.nextLine());
        Owner o = service.getById(id);
        if (o == null) {
            System.out.println("Владелец не найден.");
            return;
        }
        System.out.print("Новое имя (текущее: " + o.getName() + "): ");
        String name = sc.nextLine();
        System.out.print("Новая ДР (YYYY-MM-DD, текущая: " + o.getBirthDate() + "): ");
        String bd = sc.nextLine();

        if (!name.isBlank()) o.setName(name);
        if (!bd.isBlank())  o.setBirthDate(LocalDate.parse(bd));

        service.update(o);
        System.out.println("Владелец обновлён.");
    }

    public void deleteOwner() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите ID для удаления: ");
        long id = Long.parseLong(sc.nextLine());
        service.deleteById(id);
        System.out.println("Владелец с ID=" + id + " удалён.");
    }
}