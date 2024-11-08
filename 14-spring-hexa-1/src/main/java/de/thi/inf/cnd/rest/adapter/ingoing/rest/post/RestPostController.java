package de.thi.inf.cnd.rest.adapter.ingoing.rest.post;

import de.thi.inf.cnd.rest.domain.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class RestPostController {
    private PostService service;

    public RestPostController(PostService service) {
        this.service = service;
    }

    @PostMapping("/")
    public void createPost(@RequestBody CreatePostRequest postData) {

        // überprüfen ob postData korrekt gefüllt
        if (postData.getTitle() == null || postData.getContent() == null) {
            throw new IllegalArgumentException("Title and Content must be set");
        }
        this.service.createPost(postData.getTitle(), postData.getContent());
    }
}
