package de.thi.inf.cnd.rest.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Comment {
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String text;
    private LocalDateTime date;
    private String userRef;

    public Comment() {
        this.id = UUID.randomUUID();
    }
}
