package com.sunnysuperman.validation.impl.test.model;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class NullAwareRequest {

	@Null
	String notes;

}
