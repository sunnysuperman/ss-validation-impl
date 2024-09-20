package com.sunnysuperman.validation.impl.test.model;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class MinAwareRequest {

	@Min(value = 10)
	int intNum;

	@Min(value = 1000)
	long longNum;

	@Min(value = 20)
	float floatNum;

	@Min(value = 2000)
	double doubleNum;

}
