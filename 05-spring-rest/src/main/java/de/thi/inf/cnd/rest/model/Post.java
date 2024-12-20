package de.thi.inf.cnd.rest.model;

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

@Entity
@Data // <- wenn lombok genutzt wird, erzeugt dies getter und setter automatisch
@AllArgsConstructor // <- erzeugt über lombok einen Konstruktor, der alle Attribute setzt
public class Post {
    @Id
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    private String content;
    private LocalDateTime date;
    private String userRef;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Post() {
        this.id = UUID.randomUUID();
        this.comments = new ArrayList<>();
    }
}
