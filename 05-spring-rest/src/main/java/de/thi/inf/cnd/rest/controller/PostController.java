package de.thi.inf.cnd.rest.controller;

import de.thi.inf.cnd.rest.model.Post;
import de.thi.inf.cnd.rest.repository.PostRepositories;
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

    private final PostRepositories postRepositories;

    public PostController(PostRepositories postRepositories) {
        this.postRepositories = postRepositories;
    }

    // Auflisten
    @GetMapping
    public Iterable<Post> listPosts() {
        // Hier wird eine Liste von Posts zurückgegeben
        return postRepositories.findAll();
    }

    // Abfragen eines Elements
    @GetMapping("/{id}") // <- {...} markiert platzhalter, die beliebig gefüllt werden können im Pfad
    public Post getPost(@PathVariable UUID id) {
        // Hier wird ein Post zurückgegeben
        Optional<Post> post = this.postRepositories.findById(id);
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
        this.postRepositories.save(toSave);

        return ResponseEntity.created(location).build();
    }

    // Aktualisieren
    public void updatePost() {
        // Hier wird ein Post aktualisiert
    }

    // Löschen
    public void deletePost() {
        // Hier wird ein Post gelöscht
    }
}
