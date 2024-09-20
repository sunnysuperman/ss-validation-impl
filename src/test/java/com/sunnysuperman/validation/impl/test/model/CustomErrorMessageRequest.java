package com.sunnysuperman.validation.impl.test.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

import com.sunnysuperman.validation.api.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class CustomErrorMessageRequest {

	@NotEmpty(message = "请选择用户")
	String userId;

	@Length(max = 3, message = "备注最多3个字")
	String notes;

	@Max(value = 10000, message = "最多调整大小为1万")
	int num;

}
