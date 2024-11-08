package de.thi.inf.cnd.rest.adapter.outgoing.jpa.post;

import de.thi.inf.cnd.rest.application.ports.outgoing.PostRepository;
import de.thi.inf.cnd.rest.domain.model.Post;
import org.springframework.stereotype.Service;

@Service
public class JpaPostRepository extends PostRepository {

    private JpaPostCrudRepository repo;

    public JpaPostRepository(JpaPostCrudRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(Post post) {
        PostEntity entity = PostEntity.fromPost(post);
        this.repo.save(entity);
    }

    @Override
    public Post findById(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void update(Post post) {

    }
}
