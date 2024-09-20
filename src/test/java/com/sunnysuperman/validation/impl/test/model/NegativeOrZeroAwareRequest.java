package com.sunnysuperman.validation.impl.test.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NegativeOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class NegativeOrZeroAwareRequest {

	@NegativeOrZero
	BigDecimal decimalNum;

	@NegativeOrZero
	Double doubleNum;

}
