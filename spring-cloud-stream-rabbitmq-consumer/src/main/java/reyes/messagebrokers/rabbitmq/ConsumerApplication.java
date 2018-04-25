package reyes.messagebrokers.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@SpringBootApplication
@EnableBinding(ConsumerChannels.class)
public class ConsumerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerApplication.class);

    @Bean
    IntegrationFlow integrationFlow(ConsumerChannels channels){
        return IntegrationFlows
                .from(channels.producer())
                .handle(String.class, ((payload, headers) -> {
                    LOGGER.info("new message: "+ payload);
                    return null;
                })).get();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}