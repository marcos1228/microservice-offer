package com.offer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.offer.domain.model.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
	public List<Long> findAllByIdIn(List<Long> ids);

	public Optional<Offer> findByidProduct(Long long1);

	@Query("SELECT m FROM Offer m WHERE m.title LIKE %:title%")
	Page<Offer> findByTitle(@RequestParam("title") String title, Pageable pageable);

}
