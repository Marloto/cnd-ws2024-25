package de.thi.inf.cnd.rest.controller;

import de.thi.inf.cnd.rest.model.Post;
import de.thi.inf.cnd.rest.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepositories) {
        this.postRepository = postRepositories;
    }

    // Auflisten
    @GetMapping
    public Iterable<Post> listPosts() {
        // Hier wird eine Liste von Posts zurückgegeben
        return postRepository.findAll();
    }

    // Abfragen eines Elements
    @GetMapping("/{id}") // <- {...} markiert platzhalter, die beliebig gefüllt werden können im Pfad
    public Post getPost(@PathVariable UUID id) {
        // Hier wird ein Post zurückgegeben
        Optional<Post> post = this.postRepository.findById(id);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        return post.get();
    }

    // Hinzufügen
    @PostMapping()
    public ResponseEntity<?> addPost(@RequestBody Post post) {
        Post toSave = new Post();
        // todo fail, wenn nicht genug daten da
        toSave.setTitle(post.getTitle());
        toSave.setContent(post.getContent());
        toSave.setDate(LocalDateTime.now());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toSave.getId())
                .toUri();

        // Was passiert wenn man einfach Objekte speichert die wir von einer Schnittstelle erhalten?
        this.postRepository.save(toSave);

        return ResponseEntity.created(location).build();
    }

    // Aktualisieren
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable UUID id, @RequestBody Post post) {
        Optional<Post> oldPost = postRepository.findById(id);
        if(oldPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return postRepository.save(oldPost.map(p -> {
            p.setContent(post.getContent());
            p.setTitle(post.getTitle());
            return p;
        }).get());
    }

    // Löschen
    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable UUID id) {
        postRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
