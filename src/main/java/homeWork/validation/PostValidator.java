package homeWork.validation;

import homeWork.model.Post;

public class PostValidator implements Validator<Post> {

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
