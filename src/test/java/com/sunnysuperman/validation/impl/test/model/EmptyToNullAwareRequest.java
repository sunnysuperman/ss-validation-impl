package com.sunnysuperman.validation.impl.test.model;

import javax.validation.Valid;

import com.sunnysuperman.validation.api.EmptyToNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class EmptyToNullAwareRequest {

	@EmptyToNull
	String notes;

}
