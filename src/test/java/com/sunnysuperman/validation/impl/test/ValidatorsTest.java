package com.sunnysuperman.validation.impl.test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.sunnysuperman.validation.api.exception.CustomMessageValidationsException;
import com.sunnysuperman.validation.api.exception.ValidationsException;
import com.sunnysuperman.validation.impl.Validators;
import com.sunnysuperman.validation.impl.test.model.ArgumentServiceException;
import com.sunnysuperman.validation.impl.test.model.CustomErrorMessageRequest;
import com.sunnysuperman.validation.impl.test.model.EmptyToNullAwareRequest;
import com.sunnysuperman.validation.impl.test.model.LengthAwareRequest;
import com.sunnysuperman.validation.impl.test.model.MaxAwareRequest;
import com.sunnysuperman.validation.impl.test.model.MinAwareRequest;
import com.sunnysuperman.validation.impl.test.model.MiscRequest;
import com.sunnysuperman.validation.impl.test.model.NegativeAwareRequest;
import com.sunnysuperman.validation.impl.test.model.NegativeOrZeroAwareRequest;
import com.sunnysuperman.validation.impl.test.model.NotEmptyAwareRequest;
import com.sunnysuperman.validation.impl.test.model.NotEmptyAwareRequest.UpdateItem;
import com.sunnysuperman.validation.impl.test.model.NotNullAwareRequest;
import com.sunnysuperman.validation.impl.test.model.NullAwareRequest;
import com.sunnysuperman.validation.impl.test.model.NullToEmptyAwareRequest;
import com.sunnysuperman.validation.impl.test.model.PositiveAwareRequest;
import com.sunnysuperman.validation.impl.test.model.PositiveOrZeroAwareRequest;
import com.sunnysuperman.validation.impl.test.model.SetNullAwareRequest;

class ValidatorsTest {

	@Test
	void testCustomMessage() {
		CustomErrorMessageRequest req = new CustomErrorMessageRequest();

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (CustomMessageValidationsException ex) {
			assertEquals("请选择用户", ex.getMessage());
		}

		req.setUserId("123");
		req.setNotes("abcd");
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (CustomMessageValidationsException ex) {
			assertEquals("备注最多3个字", ex.getMessage());
		}

		req.setNotes("abc");
		req.setNum(10001);
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (CustomMessageValidationsException ex) {
			assertEquals("最多调整大小为1万", ex.getMessage());
		}

		req.setNum(10000);
		Validators.validate(req);
	}

	@Test
	void testCustomValidation() {
		NotEmptyAwareRequest req = new NotEmptyAwareRequest();
		req.setAccountId(100L);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ArgumentServiceException ex) {
			assertEquals("items", ex.getMessage());
		}

		req.setItems(Collections.singletonList(UpdateItem.of("123", 0)));
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ArgumentServiceException ex) {
			assertEquals("items.num", ex.getMessage());
		}

		req.setItems(Collections.singletonList(UpdateItem.of("123", 10)));
		Validators.validate(req);

		req.setItem(UpdateItem.of("129", 0));
		req.setItems(Arrays.asList(UpdateItem.of("123", 10), UpdateItem.of("123", 20)));
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ArgumentServiceException ex) {
			assertEquals("items.num", ex.getMessage());
		}

		req.setItem(UpdateItem.of("129", 10));
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ArgumentServiceException ex) {
			assertEquals("duplicate items.id", ex.getMessage());
		}

		req.setItems(Arrays.asList(UpdateItem.of("123", 10), UpdateItem.of("129", 20)));
		Validators.validate(req);
	}

	@Test
	void testMisc() {
		MiscRequest req = new MiscRequest();

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("notes: should not be empty", ex.getMessage());
		}

		req.setNotes("abcdef");
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("notes: length should <=3", ex.getMessage());
		}
		req.setNotes("abc");

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("num: should not be empty", ex.getMessage());
		}
		req.setNum(0L);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("num: should be positive", ex.getMessage());
		}

		req.setNum(1L);
		Validators.validate(req);
	}

	@Test
	void testNotEmpty() {
		NotEmptyAwareRequest req = new NotEmptyAwareRequest();

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("accountId: should not be empty", ex.getMessage());
			assertEquals("accountId", ex.getInvalidFieldName());
			assertEquals("should not be empty", ex.getInvalidMessage());
		}

		req.setAccountId(100L);
		req.setItems(Collections.singletonList(UpdateItem.of(null, 10)));
		req.setItems2(Collections.singletonList(UpdateItem.of(null, 10)));
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("items.id", ex.getInvalidFieldName());
		}

		req.setItems(Collections.singletonList(UpdateItem.of("123", 10)));
		Validators.validate(req);

		req.setItem(UpdateItem.of(null, 10));
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("item.id", ex.getInvalidFieldName());
		}

		req.setItem(UpdateItem.of("129", 10));
		Validators.validate(req);
	}

	@Test
	void testNotNull() {
		NotNullAwareRequest req = new NotNullAwareRequest();

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("userId: should not be null", ex.getMessage());
		}
		req.setUserId("");

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("amount: should not be null", ex.getMessage());
		}
		req.setAmount(1.56);

		Validators.validate(req);
	}

	@Test
	void testNullToEmpty() {
		NullToEmptyAwareRequest req = new NullToEmptyAwareRequest();

		Validators.validate(req);
		assertEquals("", req.getNotes());

		req.setNotes("abc");
		Validators.validate(req);
		assertEquals("abc", req.getNotes());

		req.setNotes("");
		Validators.validate(req);
		assertEquals("", req.getNotes());
	}

	@Test
	void testEmptyToNull() {
		EmptyToNullAwareRequest req = new EmptyToNullAwareRequest();

		Validators.validate(req);
		assertNull(req.getNotes());

		req.setNotes("");
		Validators.validate(req);
		assertNull(req.getNotes());

		req.setNotes("abc");
		Validators.validate(req);
		assertEquals("abc", req.getNotes());
	}

	@Test
	void testSetNull() {
		SetNullAwareRequest req = new SetNullAwareRequest();

		Validators.validate(req);
		assertNull(req.getNotes());

		req.setNotes("");
		Validators.validate(req);
		assertNull(req.getNotes());

		req.setNotes("abc");
		Validators.validate(req);
		assertNull(req.getNotes());
	}

	@Test
	void testNull() {
		NullAwareRequest req = new NullAwareRequest();

		req.setNotes("abc");
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("notes: should be null", ex.getMessage());
		}

		req.setNotes("");
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("notes: should be null", ex.getMessage());
		}

		req.setNotes(null);
		Validators.validate(req);
	}

	@Test
	void testLength() {
		LengthAwareRequest req = new LengthAwareRequest();

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("userId: length should >=6", ex.getMessage());
		}

		req.setUserId("123456");

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("others: length should >=1", ex.getMessage());
		}

		req.setOthers("abcdefghijk");

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("others: length should <=10", ex.getMessage());
		}

		req.setOthers("abcdefghij");
		Validators.validate(req);

		req.setNotes("abcd");
		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("notes: length should <=3", ex.getMessage());
		}

		req.setNotes("abc");
		Validators.validate(req);
	}

	@Test
	void testMin() {
		MinAwareRequest req = new MinAwareRequest();

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("intNum: should >=10", ex.getMessage());
		}
		req.setIntNum(10);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("longNum: should >=1000", ex.getMessage());
		}
		req.setLongNum(1000);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("floatNum: should >=20", ex.getMessage());
		}
		req.setFloatNum(20);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("doubleNum: should >=2000", ex.getMessage());
		}
		req.setDoubleNum(1999.999999);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("doubleNum: should >=2000", ex.getMessage());
		}

		req.setDoubleNum(2000);
		Validators.validate(req);
	}

	@Test
	void testMax() {
		MaxAwareRequest req = new MaxAwareRequest();
		req.setIntNum(Integer.MAX_VALUE);
		req.setLongNum(Long.MAX_VALUE);
		req.setFloatNum(Float.MAX_VALUE);
		req.setDoubleNum(Double.MAX_VALUE);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("intNum: should <=10", ex.getMessage());
		}
		req.setIntNum(10);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("longNum: should <=1000", ex.getMessage());
		}
		req.setLongNum(1000);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("floatNum: should <=20", ex.getMessage());
		}
		req.setFloatNum(20);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("doubleNum: should <=2000", ex.getMessage());
		}
		req.setDoubleNum(2000.000001);

		try {
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("doubleNum: should <=2000", ex.getMessage());
		}

		req.setDoubleNum(2000);
		Validators.validate(req);
	}

	@Test
	void testPositive() {
		PositiveAwareRequest req = new PositiveAwareRequest();
		Validators.validate(req);

		for (BigDecimal val : new BigDecimal[] { new BigDecimal("-0.000001"), BigDecimal.ZERO }) {
			try {
				req.setDecimalNum(val);
				Validators.validate(req);
				assertTrue(false);
			} catch (ValidationsException ex) {
				assertEquals("decimalNum: should be positive", ex.getMessage());
			}
		}

		req.setDecimalNum(new BigDecimal("0.000001"));
		Validators.validate(req);

		for (Double val : new Double[] { -0.000001d, 0d }) {
			try {
				req.setDoubleNum(val);
				Validators.validate(req);
				assertTrue(false);
			} catch (ValidationsException ex) {
				assertEquals("doubleNum: should be positive", ex.getMessage());
			}
		}

		req.setDoubleNum(0.000001);
		Validators.validate(req);
	}

	@Test
	void testPositiveOrZero() {
		PositiveOrZeroAwareRequest req = new PositiveOrZeroAwareRequest();
		Validators.validate(req);

		try {
			req.setDecimalNum(new BigDecimal("-0.000001"));
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("decimalNum: should >=0", ex.getMessage());
		}

		req.setDecimalNum(BigDecimal.ZERO);
		Validators.validate(req);

		req.setDecimalNum(new BigDecimal("0.000001"));
		Validators.validate(req);

		try {
			req.setDoubleNum(-0.000001);
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("doubleNum: should >=0", ex.getMessage());
		}
		req.setDoubleNum(0.00000);
		Validators.validate(req);

		req.setDoubleNum(0.000001);
		Validators.validate(req);
	}

	@Test
	void testNegative() {
		NegativeAwareRequest req = new NegativeAwareRequest();
		Validators.validate(req);

		for (BigDecimal val : new BigDecimal[] { new BigDecimal("0.000001"), BigDecimal.ZERO }) {
			try {
				req.setDecimalNum(val);
				Validators.validate(req);
				assertTrue(false);
			} catch (ValidationsException ex) {
				assertEquals("decimalNum: should be negative", ex.getMessage());
			}
		}

		req.setDecimalNum(new BigDecimal("-0.000001"));
		Validators.validate(req);

		for (Double val : new Double[] { 0.000001d, 0d }) {
			try {
				req.setDoubleNum(val);
				Validators.validate(req);
				assertTrue(false);
			} catch (ValidationsException ex) {
				assertEquals("doubleNum: should be negative", ex.getMessage());
			}
		}

		req.setDoubleNum(-0.000001);
		Validators.validate(req);
	}

	@Test
	void testNegativeOrZero() {
		NegativeOrZeroAwareRequest req = new NegativeOrZeroAwareRequest();
		Validators.validate(req);

		try {
			req.setDecimalNum(new BigDecimal("0.000001"));
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("decimalNum: should <=0", ex.getMessage());
		}

		req.setDecimalNum(BigDecimal.ZERO);
		Validators.validate(req);

		req.setDecimalNum(new BigDecimal("-0.000001"));
		Validators.validate(req);

		try {
			req.setDoubleNum(0.000001);
			Validators.validate(req);
			assertTrue(false);
		} catch (ValidationsException ex) {
			assertEquals("doubleNum: should <=0", ex.getMessage());
		}
		req.setDoubleNum(0.00000);
		Validators.validate(req);

		req.setDoubleNum(-0.000001);
		Validators.validate(req);
	}

}
