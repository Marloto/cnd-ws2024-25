package de.thi.inf.cnd.rest.application.ports.outgoing;

import de.thi.inf.cnd.rest.domain.model.Post;

public interface PostEvents {
    public void publishNewPost(Post post);
}
