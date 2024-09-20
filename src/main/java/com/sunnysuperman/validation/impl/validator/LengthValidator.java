package com.sunnysuperman.validation.impl.validator;

import com.sunnysuperman.validation.api.Length;
import com.sunnysuperman.validation.api.exception.ValidationsException;
import com.sunnysuperman.validation.impl.ContextValidationsException;
import com.sunnysuperman.validation.impl.ValidationContext;
import com.sunnysuperman.validation.impl.Validator;

public class LengthValidator implements Validator {

	@Override
	public void validate(Object obj, ValidationContext context) throws ValidationsException {
		if (obj == null) {
			obj = "";
		}
		if (!(obj instanceof String)) {
			throw new ContextValidationsException(context, "not a string");
		}
		String s = obj.toString();
		int len = s.length();
		Length meta = (Length) context.getAnnotation();
		if (len < meta.min()) {
			throw context.wrapValidationException(this::wrapValidationExceptionByMin);
		}
		if (len > meta.max()) {
			throw context.wrapValidationException(this::wrapValidationExceptionByMax);
		}
	}

	protected ValidationsException wrapValidationExceptionByMin(ValidationContext context) {
		Length meta = (Length) context.getAnnotation();
		return new ContextValidationsException(context, "length should >=" + meta.min());
	}

	protected ValidationsException wrapValidationExceptionByMax(ValidationContext context) {
		Length meta = (Length) context.getAnnotation();
		return new ContextValidationsException(context, "length should <=" + meta.max());
	}

}
