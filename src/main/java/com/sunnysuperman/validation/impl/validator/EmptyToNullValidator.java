package com.sunnysuperman.validation.impl.validator;

import com.sunnysuperman.validation.api.exception.ValidationsException;
import com.sunnysuperman.validation.impl.ContextValidationsException;
import com.sunnysuperman.validation.impl.ValidationContext;
import com.sunnysuperman.validation.impl.Validator;

public class EmptyToNullValidator implements Validator {

	@Override
	public void validate(Object obj, ValidationContext context) throws ValidationsException {
		if (obj == null) {
			return;
		}
		if (!(obj instanceof String)) {
			throw new ContextValidationsException(context, "not a string");
		}
		String s = obj.toString();
		if (s.isEmpty()) {
			context.updateFieldValue(null);
		}
	}

}
