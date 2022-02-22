package com.offer.feature;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.offer.domain.dto.request.OfferDtoRequest;
import com.offer.domain.dto.request.OfferUpdateDtoRequest;
import com.offer.domain.dto.response.OfferDtoResponse;
import com.offer.domain.dto.response.ProductDTO;
import com.offer.domain.model.Offer;

import lombok.var;

public class ScenarioFactory {

	public static ProductDTO newProduct() {
		var product = new ProductDTO();
		product.setId(Long.valueOf(1L));
		return product;
	}

	public static Offer newOffer() {
		var offer = new Offer();
		offer.setId(1l);
		offer.setIdProduct(Long.valueOf("2"));
		offer.setTitle("Oferta de natal");
		offer.setDateStart(LocalDate.of(2021, 10, 27));
		offer.setDateEnd(LocalDate.of(2024, 11, 14));
		offer.setDiscount(BigDecimal.valueOf(1L));
		return offer;
	}

	public static OfferDtoRequest offerDtoRequest() {
		var offer = new OfferDtoRequest();
		offer.setIdProduct(Long.valueOf("2"));
		offer.setTitle("Oferta de natal");
		offer.setDateStart(LocalDate.of(2021, 10, 27));
		offer.setDateEnd(LocalDate.of(2024, 11, 14));
		offer.setDiscount(BigDecimal.valueOf(1L));
		return offer;
	}

	public static OfferDtoResponse offerDtoResponse() {
		var offer = new OfferDtoResponse();
		offer.setId(1L);
		return offer;
	}

	public static Optional<Offer> newOptionalOffer() {
		return Optional.of(newOffer());

	}

	public static Optional<Offer> optionalOfferNulo() {
		return Optional.empty();
	}

	public static Pageable newPageable() {
		Pageable pageable = Pageable.ofSize(10);
		return pageable;
	}

	public static Page<Offer> newPage() {
		List<Offer> offer = new ArrayList<>();
		offer.add(new Offer());
		return new PageImpl<>(offer);

	}

	public static OfferUpdateDtoRequest newOfferRequestUpdate() {
		var offer = new OfferUpdateDtoRequest();
		offer.setTitle("Oferta de fim de ano");
		offer.setDateEnd(LocalDate.of(2021, 12, 27));
		offer.setDiscount(BigDecimal.valueOf(2));
		return offer;
	}

}
