package org.app.validation;

import org.app.model.Writer;
import org.app.repository.WriterRepository;
import org.app.repository.gson.GsonWriterRepositoryImpl;

public class WriterValidator implements Validator <Writer> {

    private final WriterRepository repository;

    public WriterValidator() {
        this.repository = new GsonWriterRepositoryImpl();
    }

    public WriterValidator(WriterRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(Writer writer) throws ValidationException {
        if (writer == null) {
            throw new ValidationException("Автор не может быть null");
        }
        if (writer.getFirstName() == null || writer.getFirstName().isBlank()) {
            throw new ValidationException("Имя автора не может быть пустым");
        }
        if (writer.getLastName() == null || writer.getLastName().isBlank()) {
            throw new ValidationException("Фамилия автора не может быть пустой");
        }
    }
}
