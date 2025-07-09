package homeWork.view;

import homeWork.controller.WriterController;
import homeWork.model.Status;
import homeWork.model.Writer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WriterView implements EntityView <Writer> {
    private final WriterController controller;
    private final Scanner scanner;

    public WriterView(WriterController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void showMenu() {
        boolean flag = false;

        while (!flag) {
            System.out.println("\n___ Управление авторами ___");
            System.out.println("1. Создать автора");
            System.out.println("2. Редактировать автора");
            System.out.println("3. Удалить автора");
            System.out.println("4. Показать всех авторов");
            System.out.println("5. Найти автора по ID");
            System.out.println("0. Назад");
            System.out.print("Ваш выбор: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> create();
                    case 2 -> edit();
                    case 3 -> delete();
                    case 4 -> showAll();
                    case 5 -> showById();
                    case 0 -> flag = true;
                    default -> System.out.println("Неверный ввод!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void create() {
        System.out.println("\n___ Создание нового автора ___");
        System.out.println("Введите имя автора: ");
        String firstName = scanner.nextLine();


        System.out.println("Введите фамилию автора: ");
        String lastName = scanner.nextLine();

        Writer writer = new Writer();
        writer.setFirstName(firstName);
        writer.setLastName(lastName);

        Writer created = controller.save(writer);
        System.out.println("Создан автор с ID: " + created.getId());
    }

    @Override
    public void edit() {
        System.out.println("\n___ Редактирование автора ___");
        System.out.print("Введите ID автора для редактирования");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Writer existing = controller.getById(id);
        if (existing == null) {
            System.out.println("Автор с таким ID не найден");
            return;
        }

        System.out.println("\n___ Текущие данные ___");
        System.out.println("Имя: " + existing.getFirstName());
        System.out.println("Фамилия: " + existing.getLastName());

        System.out.println("\n___ Введите новые данные (либо оставьте пустым чтобы не изменять ___");
        System.out.print("Новое имя: ");
        String newFirstName = scanner.nextLine();

        System.out.print("Новая фамилия: ");
        String newLastName = scanner.nextLine();

        if (!newFirstName.isEmpty()) {
            existing.setFirstName(newFirstName);
        }
        if (!newLastName.isEmpty()) {
            existing.setLastName(newLastName);
        }

        try {
            Writer updateWriter = controller.update(existing);
            System.out.println("Данные автора успешно обновлены");
            System.out.println("ID: " + updateWriter.getId());
            System.out.println("Имя: " + updateWriter.getFirstName());
            System.out.println("Фамилия: " + updateWriter.getLastName());
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении: " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        System.out.println("\n___ Удаление автора ___");
        System.out.print("Введите ID автора для удаления");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            controller.deleteById(id);
            System.out.println("Автор с ID " + id + " помечен как удаленный");
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: введите корректный числовой ID");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
        }
    }

    @Override
    public void showAll() {
        System.out.println("\n___ Показать всех авторов ___");
        List<Writer> list = controller.getAll();

        if (list.isEmpty()) {
            System.out.println("Авторы не найдены");
        }
        for (Writer writer : list) {
            System.out.println("Имя, Фамилия : " + writer.getFirstName() + " " + writer.getLastName());
        }
    }

    @Override
    public void showById() {
        System.out.println("\n___ Поиск автора по ID ___");
        try {
            System.out.print("Введите ID автора для показа");
            Long id = scanner.nextLong();
            scanner.nextLine();

            Writer existing = controller.getById(id);
            if (existing == null) {
                System.out.println("Автор с таким ID не найден");
                return;
            }

            System.out.println("\n___ Текущие данные ___");
            System.out.println("Имя: " + existing.getFirstName());
            System.out.println("Фамилия: " + existing.getLastName());
            System.out.println("Статус: " + (existing.getStatus() == Status.ACTIVE ? "Активный" : "Удален"));

            if (existing.getPosts() != null || !existing.getPosts().isEmpty()) {
                System.out.println("Колочиство постов у автора: " + existing.getPosts().size());
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: введите корректный числовой ID");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Ошибка при поиске: " + e.getMessage());
        }
    }
}
