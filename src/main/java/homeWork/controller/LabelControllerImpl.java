package homeWork.controller;

import homeWork.model.Label;
import homeWork.repository.LabelRepository;
import homeWork.validation.LabelValidator;
import homeWork.validation.ValidationException;

import java.util.List;

public class LabelControllerImpl implements LabelController {
    private final LabelRepository repository;
    private final LabelValidator validator;

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
