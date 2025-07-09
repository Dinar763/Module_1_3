package homeWork.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import homeWork.model.Post;
import homeWork.model.Status;

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
    private long nextId;

    public GsonPostRepositoryImpl() {
        this.nextId = calculateNextId();
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
    public Post save(Post post) {
        List<Post> posts = readFromFile();
        post.setId(nextId++);
        post.setStatus(Status.ACTIVE);
        posts.add(post);
        writeToFile(posts);
        return post;
    }

    @Override
    public Post update(Post post) {
        List<Post> posts = readFromFile();

        boolean updated = posts.stream()
                                .filter(l -> l.getId().equals(post.getId()))
                                .findFirst()
                                .map(existing -> {
                                    int index = posts.indexOf(existing);
                                    posts.set(index, post);
                                    writeToFile(posts);
                                    return true;
                                })
                                .orElse(false);

        return updated ? post : null;
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

    private long calculateNextId() {
        return readFromFile().stream()
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
