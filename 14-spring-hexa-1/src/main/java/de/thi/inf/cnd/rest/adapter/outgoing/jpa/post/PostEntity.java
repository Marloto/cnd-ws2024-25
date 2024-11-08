package de.thi.inf.cnd.rest.adapter.outgoing.jpa.post;

import de.thi.inf.cnd.rest.domain.model.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class PostEntity {
    @Id
    private UUID id;
    private String title;
    private String content;
    private LocalDateTime date;
    private String userRef;

    public static PostEntity fromPost(Post post) {
        PostEntity entity = new PostEntity();
        entity.setId(post.getId());
        entity.setTitle(post.getTitle());
        entity.setContent(post.getContent());
        entity.setUserRef(post.getUserRef());
        entity.setDate(post.getDate());
        return entity;
    }
}