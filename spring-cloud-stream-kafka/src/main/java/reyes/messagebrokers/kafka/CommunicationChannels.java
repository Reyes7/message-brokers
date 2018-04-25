package reyes.messagebrokers.kafka;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import reyes.messagebrokers.kafka.events.*;

interface CommunicationChannels {

    /**
     * name of channels
    */
    String USER_TASKS_OUT = "user_tasks_out";
    String USER_TASKS_IN = "user_tasks_in";

    String USER_TASKS_MV = "user_tasks_mv";
    
    String USER_COUNT_IN = "user_count_in";
    String USER_COUNT_OUT = "user_count_out";

    @Input(USER_TASKS_IN)
    KStream<String, UserTaskEvent> userTasksIn();

    @Output(USER_TASKS_OUT)
    MessageChannel userTasksOut();

    @Input(USER_COUNT_IN)
    KTable<String, Long> userCountIn();

    @Output(USER_COUNT_OUT)
    KStream<String, Long> userCountOut();
}