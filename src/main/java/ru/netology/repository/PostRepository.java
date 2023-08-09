
package netology.repository;

import netology.exception.NotFoundException;
import netology.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

// Stub
public class PostRepository {
    List<Post> posts = new CopyOnWriteArrayList<>();

    int count = 1;

    public List<Post> all() {
        return posts;
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get((int) id - 1));
    }

    public Post save(Post post) {
        if (post.getId() > posts.size()) {
            throw new NotFoundException("Not found massage");
        }
        if (post.getId() != 0) {
            posts.set((int) post.getId() - 1, post);
        } else {
            posts.add(post);
            post.setId(count);
            count++;
        }
        return post;
    }

    public void removeById(long id) {
        posts.remove((int) id - 1);
    }
}