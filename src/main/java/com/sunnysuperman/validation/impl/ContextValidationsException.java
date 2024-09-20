package com.sunnysuperman.validation.impl;

import com.sunnysuperman.validation.api.exception.ValidationsException;

@SuppressWarnings("serial")
public class ContextValidationsException extends ValidationsException {

	private final transient ValidationContext context;

	public ContextValidationsException(ValidationContext context, String invalidMessage) {
		super(context.canonicalFieldName(), invalidMessage);
		this.context = context;
	}

	public ValidationContext getContext() {
		return context;
	}

}
