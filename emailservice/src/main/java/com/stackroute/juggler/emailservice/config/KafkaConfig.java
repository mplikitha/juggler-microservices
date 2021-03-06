package com.stackroute.juggler.emailservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

//import com.fasterxml.jackson.databind.JsonDeserializer;
import com.stackroute.juggler.rsvp.domain.EventDetails;

@EnableKafka
@Configuration
public class KafkaConfig {

	@Bean
	public ConsumerFactory<String,EventDetails > consumerFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.20.1.16:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "event");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(EventDetails.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, EventDetails> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, EventDetails> factory = new ConcurrentKafkaListenerContainerFactory<String, EventDetails>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
