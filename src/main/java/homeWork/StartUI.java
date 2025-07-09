package homeWork;

import homeWork.controller.*;
import homeWork.repository.*;
import homeWork.validation.LabelValidator;
import homeWork.validation.PostValidator;
import homeWork.validation.WriterValidator;
import homeWork.view.LabelView;
import homeWork.view.MainView;
import homeWork.view.PostView;
import homeWork.view.WriterView;

import java.util.Scanner;

public class StartUI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PostRepository postRepository = new GsonPostRepositoryImpl();
        PostValidator postValidator = new PostValidator();
        PostController postController = new PostControllerImpl(postRepository, postValidator);
        PostView postView = new PostView(postController, sc);

        LabelRepository labelRepository = new GsonLabelRepositoryImpl();
        LabelValidator labelValidator = new LabelValidator(labelRepository);
        LabelController labelController = new LabelControllerImpl(labelRepository, labelValidator);
        LabelView labelView = new LabelView(labelController, sc);

        WriterRepository writerRepository = new GsonWriterRepositoryImpl();
        WriterValidator writerValidator = new WriterValidator();
        WriterController writerController = new WriterControllerImpl(writerRepository, writerValidator);
        WriterView writerView = new WriterView(writerController, sc);

        MainView mainView = new MainView(writerView, postView, labelView);
        mainView.start();

        sc.close();
    }
}
