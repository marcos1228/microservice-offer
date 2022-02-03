package com.offer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.offer.domain.dto.response.ProductDTO;
import com.offer.service.OfferService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class ProductDeleteConsumer {

	@Autowired
	private OfferService service;
	
	@RabbitListener(queues = "${product.delete.queue}", containerFactory = "productContainerFactory")
	public void consumer(ProductDTO product) {
		try {
			service.delete(product.getId());
			log.warn("Offer deleted for {}", product.getId());
		} catch (Exception e) {
			log.info("ERROR DEFAULT");
			e.getCause();
			e.getMessage();
		}
	}

}
