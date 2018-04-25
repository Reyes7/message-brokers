package reyes.messagebrokers.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reyes.messagebrokers.rabbitmq.ProducerChannels;

@RestController
@EnableBinding(ProducerChannels.class)
public class MessageController {

    private final MessageChannel consumer;

    public MessageController(ProducerChannels channels) {
        this.consumer = channels.consumer();
    }

    @PostMapping("/greet/{name}")
    public void publish(@PathVariable String name){
        String greeting = "Hello, " + name + "!";
        Message<String> message = MessageBuilder.withPayload(greeting)
                .build();

        this.consumer.send(message);
    }
}