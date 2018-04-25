package reyes.messagebrokers.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskEvent {
    private String user;
    private String taskName;
    private OffsetDateTime targetDay;
}