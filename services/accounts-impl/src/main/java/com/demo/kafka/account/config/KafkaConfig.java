package com.demo.kafka.account.config;

import com.demo.kafka.accounts.api.event.AccountEvent;
import com.demo.kafka.eventbus.EventBusSender;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.annotation.PostConstruct;

@Configuration
public class KafkaConfig {

    private final KafkaTemplate kafkaTemplate;

    private final GenericWebApplicationContext genericContext;

    public KafkaConfig(KafkaTemplate kafkaTemplate,
            GenericWebApplicationContext genericContext) {
        this.kafkaTemplate = kafkaTemplate;
        this.genericContext = genericContext;
    }

    @Bean
    public EventBusSender eventBus() {
        return new EventBusSender(kafkaTemplate);
    }

    /**
     * Configure kafka to retry event on failure
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        final ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setErrorHandler(new SeekToCurrentErrorHandler()); // <<<<<<
        return factory;
    }

    @PostConstruct
    public void createTopics() {
        genericContext.registerBean(
                AccountEvent.TOPIC,
                NewTopic.class,
                () -> new NewTopic(AccountEvent.TOPIC, AccountEvent.PARTITIONS, (short)1));
    }
}
