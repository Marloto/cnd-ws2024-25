package de.thi.inf.cnd.rest.application.ports.outgoing;

import de.thi.inf.cnd.rest.domain.model.Post;

public interface PostRepository {
    void save(Post post);
    Post findById(String id);
    void delete(String id);
    void update(Post post);
}
