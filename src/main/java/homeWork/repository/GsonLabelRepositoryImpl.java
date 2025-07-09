package homeWork.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import homeWork.model.Label;
import homeWork.model.Status;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GsonLabelRepositoryImpl implements LabelRepository {

    private final Path FILE_PATH = Path.of("src", "main", "resources", "labels.json").toAbsolutePath();
    private final Gson gson = new Gson();
    private long nextId;

    public GsonLabelRepositoryImpl() {
        this.nextId = calculateNextId();
        ensureFileExists();
    }

    @Override
    public Label getById(Long id) {
        return readFromFile().stream()
                .filter(a -> a.getId().equals(id))
                .filter(a ->  a.getStatus() == Status.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Label> getAll() {
        return readFromFile().stream()
                .filter(a ->  a.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public Label save(Label label) {
        List <Label> labels = readFromFile();
        label.setId(nextId++);
        label.setStatus(Status.ACTIVE);
        labels.add(label);
        writeToFile(labels);
        return label;
    }



    @Override
    public Label update(Label label) {
        List<Label> labels = readFromFile();

        boolean updated = labels.stream()
                                .filter(l -> l.getId().equals(label.getId()))
                                .findFirst()
                                .map(existing -> {
                                    int index = labels.indexOf(existing);
                                    labels.set(index, label);
                                    writeToFile(labels);
                                    return true;
                                })
                                .orElse(false);

        return updated ? label : null;
    }

    @Override
    public void deleteById(Long id) {
        List<Label> labels = readFromFile();

        labels.stream()
              .filter(label -> label.getId().equals(id))
              .filter(label -> label.getStatus() == Status.ACTIVE)
              .findFirst()
              .ifPresent(label -> {
                  label.setStatus(Status.DELETED);
                  writeToFile(labels);
              });
    }

    private List<Label> readFromFile() {
        try (Reader reader = Files.newBufferedReader(FILE_PATH)) {
            List<Label> labels = gson.fromJson(reader, new TypeToken<List<Label>>(){}.getType());
            return labels != null ? labels : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeToFile(List<Label> labels) {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH)) {
            gson.toJson(labels, writer);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл", e);
        }
    }

    private long calculateNextId() {
        return readFromFile().stream()
                             .mapToLong(Label::getId)
                             .max()
                             .orElse(0) + 1;
    }

    @Override
    public Optional<Label> getByName(String name) {
        List<Label> labels = readFromFile();
        return labels.stream()
                     .filter(label -> label.getName().equalsIgnoreCase(name.trim()))
                     .filter(label -> label.getStatus() == Status.ACTIVE)
                     .findFirst();
    }

    private void ensureFileExists() {
        try {
            if (!Files.exists(FILE_PATH)) {
                Files.createDirectories(FILE_PATH.getParent());
                Files.write(FILE_PATH, "[]".getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось инициализировать файл writers.json", e);
        }
    }
}
