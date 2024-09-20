package com.sunnysuperman.validation.impl.test.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class PositiveAwareRequest {

	@Positive
	BigDecimal decimalNum;

	@Positive
	Double doubleNum;

}
