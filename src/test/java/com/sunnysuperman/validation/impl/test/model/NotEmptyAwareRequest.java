package com.sunnysuperman.validation.impl.test.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.sunnysuperman.validation.api.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class NotEmptyAwareRequest implements Validated {

	@Getter
	@Setter
	@Valid
	public static class UpdateItem implements Validated {

		@NotEmpty
		private String id;

		private long num;

		public static UpdateItem of(String id, long num) {
			UpdateItem item = new UpdateItem();
			item.id = id;
			item.num = num;
			return item;
		}

		@Override
		public void validate() {
			if (num <= 0) {
				throw new ArgumentServiceException("items.num");
			}
		}
	}

	@NotEmpty
	private Long accountId;

	/** 嵌套校验，需要在字段上标记@Valid **/
	@Valid
	private List<UpdateItem> items;

	/** 如果对象类标记了@Valid注解，会自动嵌套校验 **/
	UpdateItem item;

	private List<UpdateItem> items2;

	private Long num;

	private String notes;

	@Override
	public void validate() {
		if (num != null) {
			if (num <= 0) {
				throw new ArgumentServiceException("num");
			}
			items = null;
		} else {
			if (items == null) {
				throw new ArgumentServiceException("items");
			}
			Set<String> ids = items.stream().map(UpdateItem::getId).collect(Collectors.toSet());
			if (ids.size() != items.size()) {
				throw new ArgumentServiceException("duplicate items.id");
			}
		}
	}

}
