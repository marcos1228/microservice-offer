package com.offer.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
@Data
@Entity
@Table(name = "TB_OFFER")
public class Offer {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Idproduct")
	private Long idProduct;
	
	@Column(name = "TX_TITLE")
	private String title;
	
	@Column(name = "DT_DATESTAR")
	private LocalDate dateStart;
	
	@Column(name = "DT_DATEEND")
	private LocalDate dateEnd;
	
	@Column(name = "PC_DESCONTO")
	private BigDecimal discount;
}
