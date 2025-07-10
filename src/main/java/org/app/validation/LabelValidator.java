package org.app.validation;

import org.app.model.Label;
import org.app.repository.LabelRepository;
import org.app.repository.gson.GsonLabelRepositoryImpl;

import java.util.Optional;

public class LabelValidator implements Validator<Label> {
    private final LabelRepository repository;

    public LabelValidator() {
        this.repository = new GsonLabelRepositoryImpl();
    }

    public LabelValidator(LabelRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(Label label) throws ValidationException {
        if (label == null) {
            throw new ValidationException("Label не может быть null");
        }
        if (label.getName() == null || label.getName().isBlank()) {
            throw new ValidationException("Имя label не может быть пустым");
        }

        Optional<Label> existingLabel = repository.getByName(label.getName());
        if (existingLabel.isPresent() && !existingLabel.get().getId().equals(label.getId())) {
            throw new ValidationException("Лейбл с именем '" + label.getName() + "' уже существует");
        }
    }
}
