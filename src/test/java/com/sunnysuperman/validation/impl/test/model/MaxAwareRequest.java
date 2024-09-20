package com.sunnysuperman.validation.impl.test.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class MaxAwareRequest {

	@Max(value = 10)
	int intNum;

	@Max(value = 1000)
	long longNum;

	@Max(value = 20)
	float floatNum;

	@Max(value = 2000)
	double doubleNum;

}
