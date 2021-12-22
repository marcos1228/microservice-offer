package com.offer.config.rabbitmq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.var;

@Configuration
public class ProductRabbitConfig {

	@Bean(name = "productConnectionFactory")
	public CachingConnectionFactory productConnectionFactory(@Value("${product.rabbitmq.host}") String host,
			@Value("${product.rabbitmq.username}") String username,
			@Value("${product.rabbitmq.password}") String password) {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(host);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}

	@Bean(name = "productContainerFactory")
	public SimpleRabbitListenerContainerFactory productContainerFactory(
			@Qualifier("productConnectionFactory") ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setAutoStartup(true);
		factory.setMissingQueuesFatal(false);
		factory.setStartConsumerMinInterval(3000L);
		factory.setRecoveryInterval(15000L);
		factory.setChannelTransacted(true);
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	@Bean(name = "rabbitTemplate")
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final var rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(consumerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter consumerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
