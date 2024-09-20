package com.sunnysuperman.validation.impl.test.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class NotNullAwareRequest {

	@NotNull
	private String userId;

	@NotNull
	private Double amount;

}
