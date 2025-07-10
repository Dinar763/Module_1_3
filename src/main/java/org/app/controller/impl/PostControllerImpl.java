package org.app.controller.impl;

import org.app.controller.PostController;
import org.app.model.Post;
import org.app.repository.PostRepository;
import org.app.repository.gson.GsonPostRepositoryImpl;
import org.app.validation.PostValidator;

import java.util.List;

public class PostControllerImpl implements PostController {
    private final PostRepository repository;
    private final PostValidator validator;

    public PostControllerImpl() {
        this.repository = new GsonPostRepositoryImpl();
        this.validator = new PostValidator();
    }

    public PostControllerImpl(PostRepository postRepository, PostValidator validator) {
        this.repository = postRepository;
        this.validator = validator;
    }

    @Override
    public Post getById(Long id) {
        if (id == null) throw new IllegalArgumentException("ID не может быть null");
        return repository.getById(id);
    }

    @Override
    public List<Post> getAll() {
        return repository.getAll();
    }

    @Override
    public Post save(Post post) {
        validator.validate(post);
        return repository.save(post);
    }

    @Override
    public Post update(Post post) {
        validator.validate(post);
        return repository.update(post);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) throw new IllegalArgumentException("ID не может быть null");
        repository.deleteById(id);
    }
}
