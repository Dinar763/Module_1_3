package org.app.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.app.model.Status;
import org.app.model.Writer;
import org.app.repository.WriterRepository;

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

    public GsonWriterRepositoryImpl() {
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
    public Writer save(Writer writerToSave) {
        List<Writer> writers = readFromFile();
        Long generetedID = calculateNextId(writers);
        writerToSave.setId(generetedID);
        writerToSave.setStatus(Status.ACTIVE);
        writers.add(writerToSave);
        writeToFile(writers);
        return writerToSave;
    }



    @Override
    public Writer update(Writer writerToUpdate) {
        List<Writer> updatedWriters = readFromFile()
                .stream()
                .map(existingWriters -> {
                    if (existingWriters.getId().equals(writerToUpdate.getId())) {
                        return writerToUpdate;
                    }
                    return existingWriters;
                }).toList();

        writeToFile(updatedWriters);

        return writerToUpdate;
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

    private long calculateNextId(List<Writer> writers) {
        return writers.stream()
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
