package com.sunnysuperman.validation.impl.validator;

import java.math.BigDecimal;

import com.sunnysuperman.validation.api.exception.ValidationsException;
import com.sunnysuperman.validation.impl.ContextValidationsException;
import com.sunnysuperman.validation.impl.ValidationContext;
import com.sunnysuperman.validation.impl.Validator;

public class NegativeValidator implements Validator {

	@Override
	public void validate(Object obj, ValidationContext context) throws ValidationsException {
		BigDecimal decimal = parseBigDecimal(obj, context);
		if (decimal == null) {
			return;
		}
		if (decimal.compareTo(BigDecimal.ZERO) >= 0) {
			throw context.wrapValidationException(this::wrapValidationException);
		}
	}

	protected ValidationsException wrapValidationException(ValidationContext context) {
		return new ContextValidationsException(context, "should be negative");
	}

	private static BigDecimal parseBigDecimal(Object obj, ValidationContext context) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		if (obj instanceof Number) {
			return new BigDecimal(obj.toString());
		}
		throw new ContextValidationsException(context, "not a number");
	}

}
