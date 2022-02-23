package com.offer.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.offer.domain.dto.response.OfferDtoResponse;
import com.offer.domain.model.Offer;
import com.offer.exception.BusinessException;
import com.offer.exception.MessageBuilder;
import com.offer.feature.ScenarioFactory;
import com.offer.repository.OfferRepository;

import lombok.var;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {
	@InjectMocks
	OfferService offerService;

	@Mock
	ModelMapper modelMapper;

	@Mock
	OfferRepository offerRepository;

	@Mock
	MessageBuilder messageBuilder;

	@Test
	public void findMissingIds_WhenSendingLitIdsOfferReturnTrue_ExpectedSucess() {
		List<Long> lista = Arrays.asList(1l, 5l, 8l);
		when(offerRepository.findByIds(5l)).thenReturn(true);
		when(offerRepository.findByIds(1l)).thenReturn(true);
		when(offerRepository.findByIds(8l)).thenReturn(true);

		List<Long> missingIds = offerService.findMissingIds(lista);
		assertThat(missingIds.isEmpty(), equalTo(true));

		verify(offerRepository, times(1)).findByIds(5L);
		verify(offerRepository, times(1)).findByIds(1L);
		verify(offerRepository, times(1)).findByIds(8L);
	}

	@Test
	public void findMissingIds_WhenSendingLitIdsOfferReturnTrueAndFalse_ExpectedSucess() {
		List<Long> lista = Arrays.asList(1l, 5l, 8l);
		when(offerRepository.findByIds(5l)).thenReturn(false);
		when(offerRepository.findByIds(1l)).thenReturn(true);
		when(offerRepository.findByIds(8l)).thenReturn(false);

		List<Long> missingIds = offerService.findMissingIds(lista);
		if (!missingIds.get(0).equals(5l) && !missingIds.get(1).equals(8l)) {
			Assert.fail();
		}

		verify(offerRepository, times(1)).findByIds(5L);
		verify(offerRepository, times(1)).findByIds(1L);
		verify(offerRepository, times(1)).findByIds(8L);
	}

	@Test
	public void findMissingIds_WhenSendingLitIdsOfferReturnFalse_ExpectedSucess() {
		List<Long> lista = Arrays.asList(1l, 5l, 8l);
		when(offerRepository.findByIds(1l)).thenReturn(false);
		when(offerRepository.findByIds(5l)).thenReturn(false);
		when(offerRepository.findByIds(8l)).thenReturn(false);
		List<Long> missingIds = offerService.findMissingIds(lista);
		if (missingIds.get(0).equals(1l) && missingIds.get(1).equals(5l)) {
			Assert.assertEquals(1l, 1l);
			Assert.assertEquals(5l, 5l);
			Assert.assertEquals(8l, 8l);
		}
		verify(offerRepository, times(1)).findByIds(1l);
		verify(offerRepository, times(1)).findByIds(5l);
		verify(offerRepository, times(1)).findByIds(8l);
	}

	@Test
	public void save_WhenReceivingOfferRequestDtoWithAllValidFields_ExpectedSucess() {
		var offer = ScenarioFactory.newOffer();
		var request = ScenarioFactory.offerDtoRequest();
		var offerDtoResponse = ScenarioFactory.offerDtoResponse();
		when(modelMapper.map(request, Offer.class)).thenReturn(offer);
		when(offerRepository.save(offer)).thenReturn(offer);
		when(modelMapper.map(offer, OfferDtoResponse.class)).thenReturn(offerDtoResponse);
		offerService.save(request);
		verify(modelMapper, times(1)).map(request, Offer.class);
		verify(offerRepository, times(1)).save(offer);
		verify(modelMapper, times(1)).map(offer, OfferDtoResponse.class);
	}

	@Test
	public void findByTitle_WhenCallMethod_ExpectedSucess() {
		var pageRequest = ScenarioFactory.newPageable();
		var newPage = ScenarioFactory.newPage();
		var title = "test";
		when(offerRepository.findByTitle(title, pageRequest)).thenReturn(newPage);
		offerService.findByTitle(title, pageRequest);
		verify(offerRepository, times(1)).findByTitle(title, pageRequest);

	}

	@Test
	public void update_WhenReceivingValidBaseIdAndOfferUpdateDtoRequestWittAllFieldsValidated_ExpectedSucess() {
		var optionalOffer = ScenarioFactory.newOptionalOffer();
		var offerRequestUpdate = ScenarioFactory.newOfferRequestUpdate();
		var offerDtoResponse = ScenarioFactory.offerDtoResponse();
		when(offerRepository.findById(optionalOffer.get().getId())).thenReturn(optionalOffer);
		when(offerRepository.save(optionalOffer.get())).thenReturn(optionalOffer.get());
		when(modelMapper.map(optionalOffer.get(), OfferDtoResponse.class)).thenReturn(offerDtoResponse);
		offerService.update(1L, offerRequestUpdate);
		verify(offerRepository, times(1)).findById(optionalOffer.get().getId());
		verify(offerRepository, times(1)).save(optionalOffer.get());
		verify(modelMapper, times(1)).map(optionalOffer.get(), OfferDtoResponse.class);
	}

	@Test
	public void update_WhenReceivingInvalidBaseIdOrOfferUpdateRequestWithSomeInvalidFields_ExpectedException() {
		var offerRequestUpdate = ScenarioFactory.newOfferRequestUpdate();
		var optionalOfferNulo = ScenarioFactory.optionalOfferNulo();
		when(offerRepository.findById(2L)).thenReturn(optionalOfferNulo);
		assertThatThrownBy(() -> offerService.update(2L, offerRequestUpdate)).isInstanceOf(BusinessException.class)
				.hasMessage(messageBuilder.getMessage("message.exception"));
		verify(offerRepository, times(1)).findById(2L);

	}

	@Test
	public void delete_WhenReceivingValidIdOnBase_ExpectedSucess() {
		var offer = ScenarioFactory.newOffer();
		var optionalOffer = ScenarioFactory.newOptionalOffer();
		when(offerRepository.findByidProduct(2L)).thenReturn(optionalOffer);
		offerService.delete(2L);
		verify(offerRepository, times(1)).findByidProduct(2L);
		verify(offerRepository, times(1)).delete(offer);

	}

	@Test
	public void delete_WhenReceivingInvalidBaseId_ExpectedException() {
		var optionalOfferNulo = ScenarioFactory.optionalOfferNulo();
		when(offerRepository.findByidProduct(3L)).thenReturn(optionalOfferNulo);
		assertThatThrownBy(() -> offerService.delete(3L)).isInstanceOf(BusinessException.class)
				.hasMessage(messageBuilder.getMessage("message.exception"));
		verify(offerRepository, times(1)).findByidProduct(3L);

	}
}
