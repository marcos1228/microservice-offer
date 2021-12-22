package com.offer.domain.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OfferDtoResponse {
	@ApiModelProperty(value = "id da offerta", example = "123")
	private Long id;
	@ApiModelProperty(value = "id do pedido", example = "123")
	private Long idProduct;
	@ApiModelProperty(value = "titulo da oferta", example = "Oferta do dia das mães")
	private String title;
	@ApiModelProperty(value = "data inicio da oferta", example = "yyyy/MM/dd")
	private LocalDate dateStart;
	@ApiModelProperty(value = "data fim da oferta", example = "yyyy/MM/dd")
	private LocalDate dateEnd;
	@ApiModelProperty(value = "desconto da oferta", example = "2%")
	private BigDecimal discount;
}
