package homeWork.view;

import homeWork.controller.LabelController;
import homeWork.model.Label;
import homeWork.model.Status;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LabelView implements EntityView<Label> {
    private final LabelController controller;
    private final Scanner scanner;

    public LabelView(LabelController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void showMenu() {
        boolean flag = false;

        while (!flag) {
            System.out.println("\n___ Управление лэйблами ___");
            System.out.println("1. Создать лэйбл");
            System.out.println("2. Редактировать лэйбл");
            System.out.println("3. Удалить лэйбл");
            System.out.println("4. Показать все лэйбл");
            System.out.println("5. Найти лэйбл по ID");
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
        System.out.println("\n___ Создание нового лэйбла ___");
        System.out.println("Введите название лэйбла: ");
        String name = scanner.nextLine();

        Label label = new Label();
        label.setName(name);

        try {
            Label created = controller.save(label);
            System.out.println("Создан лэйбл с ID: " + created.getId());
        } catch (Exception e) {
            System.out.println("Ошибка: " +  e.getMessage());
        }
    }

    @Override
    public void edit() {
        System.out.println("\n___ Редактирование лэйбла ___");
        System.out.print("Введите ID лэйбл для редактирования");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Label existing = controller.getById(id);
        if (existing == null) {
            System.out.println("Лэйбл с таким ID не найден");
            return;
        }

        System.out.println("\n___ Текущие данные ___");
        System.out.println("Название: " + existing.getName());

        System.out.println("\n___ Введите новые данные (либо оставьте пустым чтобы не изменять ___");
        System.out.print("Новое название: ");
        String newName = scanner.nextLine();

        if (!newName.isEmpty()) {
            existing.setName(newName);
        }

        try {
            Label updateLabel = controller.update(existing);
            System.out.println("Данные лэйбла успешно обновлены");
            System.out.println("ID: " + updateLabel.getId());
            System.out.println("Название: " + updateLabel.getName());
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении: " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        System.out.println("\n___ Удаление лэйбла ___");
        System.out.print("Введите ID лэйбла для удаления");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            controller.deleteById(id);
            System.out.println("Лейбл с ID " + id + " помечен как удаленный");
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: введите корректный числовой ID");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
        }
    }

    @Override
    public void showAll() {
        System.out.println("\n___ Список всех лэйблов ___");
        List<Label> list = controller.getAll();

        if (list.isEmpty()) {
            System.out.println("Лэйблы не найдены");
        }


        for (Label label : list) {
            System.out.println("Название: " + label.getName());
        }
    }

    @Override
    public void showById() {
        System.out.println("\n___ Поиск лэйбла по ID ___");
        try {
            System.out.print("Введите ID лэйбла для показа");
            Long id = scanner.nextLong();
            scanner.nextLine();

            Label existing = controller.getById(id);
            if (existing == null) {
                System.out.println("Лэйбл с таким ID не найден");
                return;
            }

            System.out.println("\n___ Текущие данные ___");
            System.out.println("Название: " + existing.getName());
            System.out.println("Статус: " + (existing.getStatus() == Status.ACTIVE ? "Активный" : "Удален"));

        } catch (InputMismatchException e) {
            System.out.println("Ошибка: введите корректный числовой ID");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Ошибка при поиске: " + e.getMessage());
        }
    }
}
