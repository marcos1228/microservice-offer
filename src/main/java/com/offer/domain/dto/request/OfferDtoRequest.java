package com.offer.domain.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;


import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class OfferDtoRequest {
	@ApiModelProperty(value = "id do pedido", example = "123")
	@NotNull(message = "{idProduct.notNull}")
	@Positive(message = "{idProduct.positive}")
	private Long idProduct;
	@ApiModelProperty(value = "titulo da oferta", example = "Oferta do dia das mães")
	@NotNull(message = "{title.notNull}")
	@NotBlank(message = "{title.notBlank}")
	@NotEmpty(message = "{title.notEmpty}")
	@Size(min = 5, max = 50, message = "{title.size}")
	private String title;
	@ApiModelProperty(value = "data inicio da oferta", example = "dd/MM/yyyy")
	@FutureOrPresent(message = "{dateStart.futureOrPresent}")
	@NotNull(message = "{dateStart.notNull}")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dateStart;
	@ApiModelProperty(value = "data fim da oferta", example = "dd/MM/yyyy")
	@Future(message = "{dateEnd.future}")
	@NotNull(message = "{dateEnd.notNull}")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dateEnd;
	@ApiModelProperty(value = "desconto da oferta", example = "2%")
	@NotNull(message = "{discount.notNull}")
	@Digits(integer = 6,fraction = 2, message = "{discount.digits}")
	@Range(min = 1, max = 50, message = "{discount.range}")
	@Positive
	private BigDecimal discount;
}
