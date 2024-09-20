package com.sunnysuperman.validation.impl.test.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class PositiveOrZeroAwareRequest {

	@PositiveOrZero
	BigDecimal decimalNum;

	@PositiveOrZero
	Double doubleNum;

}
