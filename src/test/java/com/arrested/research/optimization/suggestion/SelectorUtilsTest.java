package com.arrested.research.optimization.suggestion;

import org.junit.Assert;
import org.junit.Test;

public class SelectorUtilsTest {

	@Test
	public void isIdSelectorTest_positive() {
		Assert.assertTrue(SelectorUtils.isIdSelector("#foo-BAR_bat"));
	}
	
	@Test
	public void isIdSelectorTest_negative() {
		Assert.assertFalse(SelectorUtils.isIdSelector("foo-BAR_bat"));
	}
	
	@Test
	public void isIdSelectorTest_idSelectorNotFirst() {
		Assert.assertFalse(SelectorUtils.isIdSelector(".baz#foo"));
	}
}
