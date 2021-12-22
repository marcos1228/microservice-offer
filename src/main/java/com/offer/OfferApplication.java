package com.offer;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootApplication
public class OfferApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(OfferApplication.class, args);
		log.info("Microservice de offer pronto para receber requisições!");
	}
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	@Bean
	public ModelMapper mapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}
}
