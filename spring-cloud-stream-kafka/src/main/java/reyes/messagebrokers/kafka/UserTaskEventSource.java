package reyes.messagebrokers.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reyes.messagebrokers.kafka.events.UserTaskEvent;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@EnableBinding(CommunicationChannels.class)
public class UserTaskEventSource implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTaskEventSource.class);

    @Autowired
    private CommunicationChannels communicationChannels;

    @Override
    public void run(ApplicationArguments args){
        Runnable runnable = () -> {
            UserTaskEvent userTaskEvent = generateUserTaskEvent();

            Message<UserTaskEvent> inputMessage = MessageBuilder
                    .withPayload(userTaskEvent)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, userTaskEvent.getUser().getBytes())
                    .build();
            try {
                this.communicationChannels.userTasksOut().send(inputMessage);
					LOGGER.info("sent message: " + inputMessage.toString());
            } catch (Exception e) {
					LOGGER.error(e.getMessage());
            }
        };

        Executors
                .newScheduledThreadPool(1)
                .scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
    }

    private UserTaskEvent generateUserTaskEvent() {
        List<String> users = Arrays.asList("John Rambo", "Johny Bravo", "Anakin Skywalker", "Han Solo",
                "Kylo Ren", "Frodo Baggins");

        List<String> tasks = Arrays.asList("kill everyone", "defend death star", "protect a queen",
                "go on a date", "write code in Java", "go shopping", "destroy the ring", "travel to shire");

        Integer day = new Random().nextInt(10);
        String user = users.get(new Random().nextInt(users.size()));
        String task = tasks.get(new Random().nextInt(tasks.size()));

        Integer randomizer = new Random().nextInt(100);

        OffsetDateTime date = OffsetDateTime.now();
        if(randomizer % 2 == 0){
            date = date.minusDays(day.longValue());
        }else{
            date = date.plusDays(day.longValue());
        }

        return new UserTaskEvent(user, task, date);
    }
}
