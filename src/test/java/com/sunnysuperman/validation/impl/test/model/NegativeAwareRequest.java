package com.sunnysuperman.validation.impl.test.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Negative;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class NegativeAwareRequest {

	@Negative
	BigDecimal decimalNum;

	@Negative
	Double doubleNum;

}
