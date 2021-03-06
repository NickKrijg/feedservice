package com.kwetter.feed.rabbit;

import com.kwetter.feed.models.Post;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private String exchange = RabbitMQConfig.topicExchangeName;

    private String routingKey = RabbitMQConfig.postRoutingKey;

    public void send(Post post) {
        rabbitTemplate.convertAndSend(exchange, routingKey, post);
        System.out.println("Send msg = " + post);
    }
}
