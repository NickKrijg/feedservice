package com.kwetter.feed.rabbit;

import com.kwetter.feed.models.Post;
import com.kwetter.feed.service.FeedService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitMQConsumer {

    @Autowired
    private FeedService feedService;

    @RabbitListener(queuesToDeclare = @Queue(name = RabbitMQConfig.queueForget, durable = "true"))
    public void receivedForget(String username) {
        System.out.println("Forget: received " + username);
        feedService.deleteAllByUsername(username);
    }

    @RabbitListener(queues = RabbitMQConfig.queueForget)
    public void receivedForget2(String username) {
        System.out.println("Forget2: received " + username);
        feedService.deleteAllByUsername(username);
    }

    @RabbitListener(queues = RabbitMQConfig.queueFeed)
    public void receivedFeed(Iterable<Post> posts){
        System.out.println("New cache data");
        List<Post> result = (List<Post>) posts;
        feedService.setFeed(result);
    }
}
