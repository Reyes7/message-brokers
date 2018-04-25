package reyes.messagebrokers.kafka;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import reyes.messagebrokers.kafka.events.UserTaskEvent;

import java.time.OffsetDateTime;

import static org.apache.kafka.streams.kstream.Materialized.as;
import static reyes.messagebrokers.kafka.CommunicationChannels.*;

@Component
public class UserTasksEventProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTasksEventProcessor.class);

    @StreamListener
    @SendTo(USER_COUNT_OUT)
    public KStream<String, Long> process(@Input(USER_TASKS_IN)KStream<String, UserTaskEvent> events){
        return events
                .filter((key, value) -> value.getTargetDay().isAfter(OffsetDateTime.now()))
                .map((key, value) -> new KeyValue<>(value.getUser(), "0"))
                .groupByKey()
                .count(as(USER_TASKS_MV))
                .toStream();
    }
}