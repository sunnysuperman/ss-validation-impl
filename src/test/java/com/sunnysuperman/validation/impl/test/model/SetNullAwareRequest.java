package com.sunnysuperman.validation.impl.test.model;

import javax.validation.Valid;

import com.sunnysuperman.validation.api.SetNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class SetNullAwareRequest {

	@SetNull
	String notes;

}
