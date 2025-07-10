package org.app.controller.impl;

import org.app.controller.LabelController;
import org.app.model.Label;
import org.app.repository.LabelRepository;
import org.app.repository.gson.GsonLabelRepositoryImpl;
import org.app.validation.LabelValidator;
import org.app.validation.ValidationException;

import java.util.List;

public class LabelControllerImpl implements LabelController {
    private final LabelRepository repository;
    private final LabelValidator validator;

    public LabelControllerImpl() {
        this.repository = new GsonLabelRepositoryImpl();
        this.validator = new LabelValidator();
    }

    public LabelControllerImpl(LabelRepository repository, LabelValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public Label getById(Long id) {
        if (id == null) throw new IllegalArgumentException("ID не может быть null");
        return repository.getById(id);
    }

    @Override
    public List<Label> getAll() {
        return repository.getAll();
    }

    @Override
    public Label save(Label label) {
        try {
            validator.validate(label);
            return repository.save(label);
        } catch (ValidationException e) {
            throw new IllegalStateException("Ошибка валидации: " + e.getMessage(), e);
        }

    }

    @Override
    public Label update(Label label) {
        validator.validate(label);
        return repository.update(label);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) throw new IllegalArgumentException("ID не может быть null");
        repository.deleteById(id);
    }
}
