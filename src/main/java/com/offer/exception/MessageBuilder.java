package com.offer.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageBuilder {

	@Autowired
	private MessageSource messageSource;

	public String getMessage(String messageCode) {
		return this.messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());

	}

	public String getMessage(String messageCode, Object... args) {
		return this.messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale());
	}
}
