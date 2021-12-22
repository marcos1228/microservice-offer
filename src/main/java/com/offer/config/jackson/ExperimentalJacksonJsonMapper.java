package com.offer.config.jackson;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

public class ExperimentalJacksonJsonMapper extends Jackson2JsonMessageConverter {
	
	@Override
	public Object fromMessage(Message message, Object conversionHint) {
		message.getMessageProperties().setContentType("application/json");
		return super.fromMessage(message, conversionHint);
	}
}
