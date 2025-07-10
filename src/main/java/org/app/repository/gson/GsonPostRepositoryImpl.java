package org.app.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.app.model.Post;
import org.app.model.Status;
import org.app.repository.PostRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonPostRepositoryImpl implements PostRepository {

    private final Path FILE_PATH = Path.of("src", "main", "resources", "posts.json").toAbsolutePath();
    private final Gson gson = new Gson();

    public GsonPostRepositoryImpl() {
        ensureFileExists();
    }

    @Override
    public Post getById(Long id) {
        return readFromFile().stream()
                             .filter(a -> a.getId().equals(id))
                             .filter(a ->  a.getStatus() == Status.ACTIVE)
                             .findFirst()
                             .orElse(null);
    }

    @Override
    public List<Post> getAll() {
        return readFromFile().stream()
                             .filter(a ->  a.getStatus() == Status.ACTIVE)
                             .collect(Collectors.toList());
    }

    @Override
    public Post save(Post postToSave) {
        List<Post> posts = readFromFile();
        Long generetedID = calculateNextId(posts);
        postToSave.setId(generetedID);
        postToSave.setStatus(Status.ACTIVE);
        posts.add(postToSave);
        writeToFile(posts);
        return postToSave;
    }

    @Override
    public Post update(Post postToUpdate) {
        List<Post> updatedPosts = readFromFile()
                .stream()
                .map(existingPosts -> {
                    if (existingPosts.getId().equals(postToUpdate.getId())) {
                        return postToUpdate;
                    }
                    return existingPosts;
                }).toList();

        writeToFile(updatedPosts);

        return postToUpdate;
    }

    @Override
    public void deleteById(Long id) {
        List<Post> posts = readFromFile();
        posts.stream()
             .filter(a -> a.getId().equals(id))
             .filter(a ->  a.getStatus() == Status.ACTIVE)
             .findFirst()
             .ifPresent(a -> {
                 a.setStatus(Status.DELETED);
                 writeToFile(posts);
             });
    }

    private List<Post> readFromFile() {
        try (Reader reader = Files.newBufferedReader(FILE_PATH)){
            List<Post> posts = gson.fromJson(reader, new TypeToken<List<Post>>(){}.getType());
            return posts != null ? posts : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeToFile(List<Post> posts) {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH)) {
            gson.toJson(posts, writer);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл", e);
        }
    }

    private long calculateNextId(List<Post> posts) {
        return posts.stream()
                             .mapToLong(Post::getId)
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
