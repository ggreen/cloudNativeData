package com.vmware.data.demo.retail.store.analytics.streams;

import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqAmpqConfig
{
    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public ConnectionNameStrategy connectionNameStrategy()
    {
        return (connection) -> applicationName;
    }
}
