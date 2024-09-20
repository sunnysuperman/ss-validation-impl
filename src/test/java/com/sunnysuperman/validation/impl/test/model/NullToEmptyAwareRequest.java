package com.sunnysuperman.validation.impl.test.model;

import javax.validation.Valid;

import com.sunnysuperman.validation.api.NullToEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class NullToEmptyAwareRequest {

	@NullToEmpty
	String notes;

}
