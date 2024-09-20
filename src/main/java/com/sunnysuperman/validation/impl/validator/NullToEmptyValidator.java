package com.sunnysuperman.validation.impl.validator;

import com.sunnysuperman.validation.api.exception.ValidationsException;
import com.sunnysuperman.validation.impl.ValidationContext;
import com.sunnysuperman.validation.impl.Validator;

public class NullToEmptyValidator implements Validator {

	@Override
	public void validate(Object obj, ValidationContext context) throws ValidationsException {
		if (obj != null) {
			return;
		}
		context.updateFieldValue("");
	}

}
