
package ru.repository;

import ru.exception.NotFoundException;
import ru.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private final Map<Long, Post> postMap = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);


    public List<Post> all() {
        return postMap.values().stream().toList();
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(postMap.get(id));
    }

    public Post save(Post post) {
        long count = counter.getAndIncrement();
        if (post.getId() > postMap.size()) {
            throw new NotFoundException("Not found massager");
        }
        if (post.getId() != 0) {
            postMap.put(post.getId(), post);
        } else {
            post.setId(count);
            postMap.put(post.getId(), post);

        }
        return post;
    }

    public void removeById(long id) {
        postMap.remove(id);
    }
}