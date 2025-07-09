package homeWork.view;

import homeWork.controller.PostController;
import homeWork.model.Post;
import homeWork.model.Status;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PostView implements EntityView<Post>{
    private final PostController controller;
    private final Scanner scanner;

    public PostView(PostController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void showMenu() {
        boolean flag = false;

        while (!flag) {
            System.out.println("\n___ Управление постами ___");
            System.out.println("1. Создать пост");
            System.out.println("2. Редактировать пост");
            System.out.println("3. Удалить пост");
            System.out.println("4. Показать все посты");
            System.out.println("5. Найти пост по ID");
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
        System.out.println("\n___ Создание нового поста ___");
        System.out.println("Введите название поста: ");
        String title = scanner.nextLine();


        System.out.println("Введите описание поста: ");
        String content = scanner.nextLine();

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        Post created = controller.save(post);
        System.out.println("Создан пост с ID: " + created.getId());
    }

    @Override
    public void edit() {
        System.out.println("\n___ Редактирование поста ___");
        System.out.print("Введите ID поста для редактирования");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Post existing = controller.getById(id);
        if (existing == null) {
            System.out.println("Пост с таким ID не найден");
            return;
        }

        System.out.println("\n___ Текущие данные ___");
        System.out.println("Название: " + existing.getTitle());
        System.out.println("Описание: " + existing.getContent());

        System.out.println("\n___ Введите новые данные (либо оставьте пустым чтобы не изменять ___");
        System.out.print("Новое название: ");
        String newTitle = scanner.nextLine();

        System.out.print("Новое описание: ");
        String newContent = scanner.nextLine();

        if (!newTitle.isEmpty()) {
            existing.setTitle(newTitle);
        }
        if (!newContent.isEmpty()) {
            existing.setContent(newContent);
        }

        try {
            Post updatePost = controller.update(existing);
            System.out.println("Данные поста успешно обновлены");
            System.out.println("ID: " + updatePost.getId());
            System.out.println("Название: " + updatePost.getTitle());
            System.out.println("Описание: " + updatePost.getContent());
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении: " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        System.out.println("\n___ Удаление поста ___");
        System.out.print("Введите ID поста для удаления");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            controller.deleteById(id);
            System.out.println("Пост с ID " + id + " помечен как удаленный");
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: введите корректный числовой ID");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
        }
    }

    @Override
    public void showAll() {
        System.out.println("\n___ Список всех постов ___");
        List<Post> list = controller.getAll();

        if (list.isEmpty()) {
            System.out.println("Посты не найдены");
        }


        for (Post post : list) {
            System.out.println("Название: " + post.getTitle() + " ,описание: " + post.getContent());
        }
    }

    @Override
    public void showById() {
        System.out.println("\n___ Поиск поста по ID ___");
        try {
            System.out.print("Введите ID поста для показа");
            Long id = scanner.nextLong();
            scanner.nextLine();

            Post existing = controller.getById(id);
            if (existing == null) {
                System.out.println("Пост с таким ID не найден");
                return;
            }

            System.out.println("\n___ Текущие данные ___");
            System.out.println("Название: " + existing.getTitle());
            System.out.println("Описание: " + existing.getContent());
            System.out.println("Статус: " + (existing.getStatus() == Status.ACTIVE ? "Активный" : "Удален"));

            if (existing.getLabels() != null || !existing.getLabels().isEmpty()) {
                System.out.println("Количество labels: " + existing.getLabels().size());
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: введите корректный числовой ID");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Ошибка при поиске: " + e.getMessage());
        }
    }
}
