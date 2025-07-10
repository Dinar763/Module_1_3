package org.app.controller.impl;

import org.app.controller.WriterController;
import org.app.model.Writer;
import org.app.repository.WriterRepository;
import org.app.repository.gson.GsonWriterRepositoryImpl;
import org.app.validation.WriterValidator;

import java.util.List;

public class WriterControllerImpl implements WriterController {
    private final WriterRepository repository;
    private final WriterValidator validator;

    public WriterControllerImpl() {
        this.repository = new GsonWriterRepositoryImpl();
        this.validator = new WriterValidator();
    }

    public WriterControllerImpl(WriterRepository repository, WriterValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public Writer getById(Long id) {
        if (id == null) throw new IllegalArgumentException("ID не может быть null");
        return repository.getById(id);
    }

    @Override
    public List<Writer> getAll() {
        return repository.getAll();
    }

    @Override
    public Writer save(Writer writer) {
        validator.validate(writer);
        return repository.save(writer);
    }

    @Override
    public Writer update(Writer writer) {
        validator.validate(writer);
        return repository.update(writer);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) throw new IllegalArgumentException("ID не может быть null");
        repository.deleteById(id);
    }
}
