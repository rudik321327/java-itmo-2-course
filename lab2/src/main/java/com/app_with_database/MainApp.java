package com.app_with_database;

import com.app_with_database.controller.OwnerController;
import com.app_with_database.controller.PetController;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        PetController petCtrl = new PetController();
        OwnerController ownerCtrl = new OwnerController();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить питомца");
            System.out.println("2. Список питомцев");
            System.out.println("3. Получить питомца по ID");
            System.out.println("4. Обновить питомца");
            System.out.println("5. Удалить питомца");
            System.out.println("6. Добавить владельца");
            System.out.println("7. Список владельцев");
            System.out.println("8. Получить владельца по ID");
            System.out.println("9. Обновить владельца");
            System.out.println("10. Удалить владельца");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1"  -> petCtrl.createPet();
                case "2" -> petCtrl.listPets();
                case "3" -> petCtrl.getPetById();
                case "4" -> petCtrl.updatePet();
                case "5" -> petCtrl.deletePet();
                case "6" -> ownerCtrl.createOwner();
                case "7" -> ownerCtrl.listOwners();
                case "8" -> ownerCtrl.getOwnerById();
                case "9" -> ownerCtrl.updateOwner();
                case "10" -> {
                    ownerCtrl.deleteOwner();
                    break;
                }
                case "0" -> {
                    JPAUtil.close();
                    System.out.println("Выход.");
                    System.exit(0);
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
}
