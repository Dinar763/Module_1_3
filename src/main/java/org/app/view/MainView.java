package org.app.view;

import org.app.controller.*;
import org.app.controller.impl.LabelControllerImpl;
import org.app.controller.impl.PostControllerImpl;
import org.app.controller.impl.WriterControllerImpl;
import org.app.repository.*;
import org.app.repository.gson.GsonLabelRepositoryImpl;
import org.app.repository.gson.GsonPostRepositoryImpl;
import org.app.repository.gson.GsonWriterRepositoryImpl;
import org.app.validation.LabelValidator;
import org.app.validation.PostValidator;
import org.app.validation.WriterValidator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    private final Scanner scanner;

    public MainView() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        PostRepository postRepository = new GsonPostRepositoryImpl();
        PostValidator postValidator = new PostValidator();
        PostController postController = new PostControllerImpl(postRepository, postValidator);
        PostView postView = new PostView(postController, scanner);

        LabelRepository labelRepository = new GsonLabelRepositoryImpl();
        LabelValidator labelValidator = new LabelValidator(labelRepository);
        LabelController labelController = new LabelControllerImpl(labelRepository, labelValidator);
        LabelView labelView = new LabelView(labelController, scanner);

        WriterRepository writerRepository = new GsonWriterRepositoryImpl();
        WriterValidator writerValidator = new WriterValidator();
        WriterController writerController = new WriterControllerImpl(writerRepository, writerValidator);
        WriterView writerView = new WriterView(writerController, scanner);

        boolean flag = false;

        while (!flag) {
            System.out.println("\n+++ Главное меню +++");
            System.out.println("1. Работа с постами");
            System.out.println("2. Работа с авторами");
            System.out.println("3. Работа с лейблами");
            System.out.println("0. Выход");
            System.out.print("Сделайте выбор: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> postView.showMenu();
                    case 2 -> writerView.showMenu();
                    case 3 -> labelView.showMenu();
                    case 0 -> flag = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число");
                scanner.nextLine();
            }
        }
        System.out.println("Программа завершена");
        scanner.close();
    }
}
