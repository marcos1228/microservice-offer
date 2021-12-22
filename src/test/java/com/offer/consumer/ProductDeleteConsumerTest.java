package com.offer.consumer;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.offer.feature.ScenarioFactory;
import com.offer.service.OfferService;

import lombok.var;

@RunWith(MockitoJUnitRunner.class)
public class ProductDeleteConsumerTest {
	@InjectMocks
	ProductDeleteConsumer productDeleteConsumer;

	@Mock
	OfferService offerService;

	@Test
	public void consumer_WhenRecebIdProductValid_ExpectedSucess() {
		var product = ScenarioFactory.newProduct();
		this.productDeleteConsumer.consumer(product);
		verify(offerService, timeout(1)).delete(product.getId());
	}

	@Test
	public void consumer_WhenReceivedIproductInvalid_ExpectedSucess() {
		var product = ScenarioFactory.newProduct();
		doThrow(new RuntimeException()).when(offerService).delete(product.getId());
		productDeleteConsumer.consumer(product);
		verify(offerService, timeout(1)).delete(product.getId());
	}
}
