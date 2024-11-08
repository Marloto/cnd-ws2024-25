package de.thi.inf.cnd.rest.application;

import de.thi.inf.cnd.rest.application.ports.outgoing.PostEvents;
import de.thi.inf.cnd.rest.application.ports.outgoing.PostRepository;
import de.thi.inf.cnd.rest.domain.PostService;
import de.thi.inf.cnd.rest.domain.model.Post;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {
    private final PostEvents events;
    private final PostRepository posts;

    public PostServiceImpl(PostEvents events, PostRepository posts) {
        this.events = events;
        this.posts = posts;
    }
    @Override
     public Post createPost(String title, String content) {
        // Post erzeugen
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setDate(LocalDateTime.now());

        // Post persistieren
        this.posts.save(post);

        // Post "event" verteilen
        this.events.publishNewPost(post);
        // Post zurück geben
        return post;
    }
}
