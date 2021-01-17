package io.pivotal.pde.demo.cloudNativeData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import solutions.nyla.apacheKafka.ApacheKafka;

@Configuration
public class KafkaConfig
{
    @Value("${BOOTSTRAP_SERVERS_CONFIG}")
    private String kafkaConfig;

    @Bean
    public ApacheKafka apacheKafka()
    {
        return ApacheKafka.connect();
    }

}
