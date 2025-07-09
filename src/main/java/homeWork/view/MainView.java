package homeWork.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    private final WriterView writerView;
    private final PostView postView;
    private final LabelView labelView;
    private final Scanner scanner;

    public MainView(WriterView writerView, PostView postView, LabelView labelView) {
        this.writerView = writerView;
        this.postView = postView;
        this.labelView = labelView;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
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
    }
}
