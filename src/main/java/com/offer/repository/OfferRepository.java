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
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Offer c WHERE c.id = :id")

	boolean findByIds(Long id);
	public Optional<Offer> findByidProduct(Long idProduct);

	@Query("SELECT m FROM Offer m WHERE m.title LIKE %:title%")
	Page<Offer> findByTitle(@RequestParam("title") String title, Pageable pageable);

}
