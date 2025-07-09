package homeWork.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import homeWork.model.Status;
import homeWork.model.Writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonWriterRepositoryImpl implements WriterRepository {

    private final Path FILE_PATH = Path.of("src", "main", "resources", "writers.json").toAbsolutePath();
    private final Gson gson = new Gson();
    private long nextId;

    public GsonWriterRepositoryImpl() {
        this.nextId = calculateNextId();
        ensureFileExists();
    }

    @Override
    public Writer getById(Long id) {
        return readFromFile().stream()
                             .filter(a -> a.getId().equals(id))
                             .filter(a ->  a.getStatus() == Status.ACTIVE)
                             .findFirst()
                             .orElse(null);
    }

    @Override
    public List<Writer> getAll() {
        return readFromFile().stream()
                             .filter(a ->  a.getStatus() == Status.ACTIVE)
                             .collect(Collectors.toList());
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> writers = readFromFile();
        writer.setId(nextId++);
        writer.setStatus(Status.ACTIVE);
        writers.add(writer);
        writeToFile(writers);
        return writer;
    }



    @Override
    public Writer update(Writer writer) {
        List<Writer> writers = readFromFile();

        boolean updated = writers.stream()
                               .filter(l -> l.getId().equals(writer.getId()))
                               .findFirst()
                               .map(existing -> {
                                   int index = writers.indexOf(existing);
                                   writers.set(index, writer);
                                   writeToFile(writers);
                                   return true;
                               })
                               .orElse(false);

        return updated ? writer : null;
    }

    @Override
    public void deleteById(Long id) {
        List<Writer> writers = readFromFile();
        writers.stream()
                      .filter(a -> a.getId().equals(id))
                      .filter(a ->  a.getStatus() == Status.ACTIVE)
                      .findFirst()
                      .ifPresent(a -> {
                          a.setStatus(Status.DELETED);
                          writeToFile(writers);
                      });
    }

    private List<Writer> readFromFile() {
        try (Reader reader = Files.newBufferedReader(FILE_PATH)) {
            List<Writer> writers = gson.fromJson(reader, new TypeToken<List<Writer>>(){}.getType());
            return writers != null ? writers : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeToFile(List<Writer> writers) {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH)) {
            gson.toJson(writers, writer);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл", e);
        }
    }

    private long calculateNextId() {
        return readFromFile().stream()
                             .mapToLong(Writer::getId)
                             .max()
                             .orElse(0) + 1;
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
