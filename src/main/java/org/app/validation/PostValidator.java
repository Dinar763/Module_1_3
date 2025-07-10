package org.app.validation;

import org.app.model.Post;
import org.app.repository.PostRepository;
import org.app.repository.gson.GsonPostRepositoryImpl;

public class PostValidator implements Validator<Post> {

    private final PostRepository repository;

    public PostValidator() {
        this.repository = new GsonPostRepositoryImpl();
    }

    public PostValidator(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(Post post) throws ValidationException {
        if (post == null) {
            throw new ValidationException("Пост не может быть null");
        }
        if (post.getTitle() == null || post.getTitle().isBlank()) {
            throw new ValidationException("Имя поста не может быть пустым");
        }
        if (post.getContent() == null || post.getContent().isBlank()) {
            throw new ValidationException("Содержание поста не может быть пустым");
        }
    }
}
