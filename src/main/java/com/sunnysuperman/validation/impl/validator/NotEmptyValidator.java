package com.sunnysuperman.validation.impl.validator;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import com.sunnysuperman.validation.api.exception.ValidationsException;
import com.sunnysuperman.validation.impl.ContextValidationsException;
import com.sunnysuperman.validation.impl.ValidationContext;
import com.sunnysuperman.validation.impl.Validator;

public class NotEmptyValidator implements Validator {

	@Override
	public void validate(Object obj, ValidationContext context) throws ValidationsException {
		if (isEmpty(obj)) {
			throw context.wrapValidationException(this::wrapValidationException);
		}
	}

	protected ValidationsException wrapValidationException(ValidationContext context) {
		return new ContextValidationsException(context, "should not be empty");
	}

	protected boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if (value instanceof String) {
			return ((String) value).isEmpty();
		}
		if (value instanceof Collection) {
			return isCollectionEmpty(value);
		}
		if (value.getClass().isArray()) {
			return isArrayEmpty(value);
		}
		if (value instanceof Map) {
			return isMapEmpty(value);
		}
		return false;
	}

	private static boolean isCollectionEmpty(Object value) {
		Collection<?> collection = (Collection<?>) value;
		if (collection.isEmpty()) {
			return true;
		}
		for (Object element : collection) {
			if (element == null) {
				return true;
			}
		}
		return false;
	}

	private static boolean isArrayEmpty(Object value) {
		int arrayLength = Array.getLength(value);
		if (arrayLength == 0) {
			return true;
		}
		for (int i = 0; i < arrayLength; i++) {
			Object element = Array.get(value, i);
			if (element == null) {
				return true;
			}
		}
		return false;
	}

	private static boolean isMapEmpty(Object value) {
		Map<?, ?> map = (Map<?, ?>) value;
		if (map.isEmpty()) {
			return true;
		}
		return map.entrySet().stream().anyMatch(i -> i.getKey() == null || i.getValue() == null);
	}

}
