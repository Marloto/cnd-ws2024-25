package de.thi.inf.cnd.rest.adapter.outgoing.mqtt;

import de.thi.inf.cnd.rest.application.ports.outgoing.PostEvents;
import de.thi.inf.cnd.rest.domain.model.Post;
import org.springframework.stereotype.Service;

@Service
public class MqttService implements PostEvents {
    @Override
    public void publishNewPost(Post post) {

    }
}
