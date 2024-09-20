package com.sunnysuperman.validation.impl.test.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import com.sunnysuperman.validation.api.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class MiscRequest {

	@NotEmpty
	@Length(max = 3)
	String notes;

	@NotEmpty
	@Positive
	Long num;

}
