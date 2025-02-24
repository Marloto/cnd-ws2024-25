package de.thi.inf.cnd.rest.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Post {
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    private String content;
    private LocalDateTime date;
    private String userRef;
    private List<Comment> comments;

    public Post() {
        this.id = UUID.randomUUID();
        this.comments = new ArrayList<>();
    }
}
