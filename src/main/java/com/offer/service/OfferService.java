package com.offer.service;


import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.offer.domain.dto.request.OfferDtoRequest;
import com.offer.domain.dto.request.OfferUpdateDtoRequest;
import com.offer.domain.dto.response.OfferDtoResponse;
import com.offer.domain.model.Offer;
import com.offer.exception.BusinessException;
import com.offer.exception.MessageBuilder;
import com.offer.repository.OfferRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OfferService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private MessageBuilder messageBuilder;

	public OfferDtoResponse getOfferById(Long id) {
		log.info("id [{}] will be for search for!", id);
		Offer offer = offerRepository.findById(id).orElseThrow(() -> new BusinessException(messageBuilder.getMessage("message.exception")));
		log.warn("The return [{}]", offer);
		return modelMapper.map(offer, OfferDtoResponse.class);
	}


	public Page<OfferDtoResponse> findByTitle(String title, Pageable pageable) {
		Page<Offer> list = offerRepository.findByTitle(title, pageable);
		log.info("Offers will be listed in pageable form.");
		return list.map(item -> modelMapper.map(item, OfferDtoResponse.class));
	}

	@Transactional
	public OfferDtoResponse save(OfferDtoRequest offerDtoRequest) {
		Offer offer = modelMapper.map(offerDtoRequest, Offer.class);
		log.info("Successfully saved!");
		return modelMapper.map(offerRepository.save(offer), OfferDtoResponse.class);
	}

	@Transactional
	public OfferDtoResponse update(Long id, OfferUpdateDtoRequest dto) {
		Offer offer = offerRepository.findById(id)
				.orElseThrow(() -> new BusinessException(messageBuilder.getMessage("message.exception")));
		log.info("id [{}] will be for updated by", id);
		modelMapper.map(dto, offer);
		log.warn("The updated [{}]", offer);
		return modelMapper.map(offerRepository.save(offer), OfferDtoResponse.class);

	}

	@Transactional
	public void delete(Long id) {
		log.info("The id delete [{}]", id);
		Offer offer = offerRepository.findByidProduct(id)
				.orElseThrow(() -> new BusinessException(messageBuilder.getMessage("message.exception")));
		log.warn("The product delete [{}]", offer);
		offerRepository.delete(offer);
	}
}
