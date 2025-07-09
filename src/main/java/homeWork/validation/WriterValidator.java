package homeWork.validation;

import homeWork.model.Writer;

public class WriterValidator implements Validator <Writer> {
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
