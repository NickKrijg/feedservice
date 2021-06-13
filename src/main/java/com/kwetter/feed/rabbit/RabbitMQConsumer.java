package com.kwetter.feed.rabbit;

import com.kwetter.feed.models.Post;
import com.kwetter.feed.service.FeedService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @Autowired
    private FeedService feedService;

    @RabbitListener(queuesToDeclare = @Queue(name = RabbitMQConfig.queuePost, durable = "true"))
    public void receivedMessages(Post post) {
        System.out.println("Received: " + post.toString());
    }

    @RabbitListener(queuesToDeclare = @Queue(name = RabbitMQConfig.queueForget, durable = "true"))
    public void receivedForget(String username) {
        System.out.println("Forget: received " + username);
        feedService.deleteAllByUsername(username);
    }
}
