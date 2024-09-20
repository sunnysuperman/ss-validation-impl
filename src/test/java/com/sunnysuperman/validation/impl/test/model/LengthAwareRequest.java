package com.sunnysuperman.validation.impl.test.model;

import javax.validation.Valid;

import com.sunnysuperman.validation.api.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class LengthAwareRequest {

	@Length(min = 6)
	String userId;

	@Length(max = 3)
	String notes;

	@Length(min = 1, max = 10)
	String others;

}
