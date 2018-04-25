package reyes.messagebrokers.kafka;

import org.apache.kafka.streams.kstream.KTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import static reyes.messagebrokers.kafka.CommunicationChannels.USER_COUNT_IN;

@Component
public class PageCountSink {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageCountSink.class);

    @StreamListener
    public void process(@Input(USER_COUNT_IN) KTable<String, Long> counts){
        counts
                .toStream()
                .foreach((key, value) -> LOGGER.info(key + " = " + value));
    }
}