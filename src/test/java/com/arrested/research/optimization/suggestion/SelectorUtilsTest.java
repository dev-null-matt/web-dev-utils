package com.arrested.research.optimization.suggestion;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class SelectorUtilsTest {

	@Test
	public void isClassSelectorTest_positive() {
		Assert.assertTrue(SelectorUtils.isClassSelector(".foo-BAR_bat h1"));
	}
	
	@Test
	public void isClassSelectorTest_negative() {
		Assert.assertFalse(SelectorUtils.isClassSelector("foo-BAR_bat"));
	}
	
	@Test
	public void isClassSelectorTest_classSelectorNotFirst() {
		Assert.assertFalse(SelectorUtils.isClassSelector("#baz.foo"));
	}
	
	@Test
	public void isIdSelectorTest_positive() {
		Assert.assertTrue(SelectorUtils.isIdSelector("#foo-BAR_bat div"));
	}
	
	@Test
	public void isIdSelectorTest_negative() {
		Assert.assertFalse(SelectorUtils.isIdSelector("foo-BAR_bat"));
	}
	
	@Test
	public void isIdSelectorTest_idSelectorNotFirst() {
		Assert.assertFalse(SelectorUtils.isIdSelector(".baz#foo"));
	}
	
	@Test
	public void isTagSelectorTest_positiveWithClass() {
		Assert.assertTrue(SelectorUtils.isTagSelector("h1.foo"));
	}

	@Test
	public void isTagSelectorTest_positiveWithId() {
		Assert.assertTrue(SelectorUtils.isTagSelector("h1#foo"));
	}

	@Test
	public void isTagSelectorTest_negatie() {
		Assert.assertFalse(SelectorUtils.isTagSelector("#foo"));
	}
	
	@Test
	public void tokenizeSelectorTest_happyPath() {
		
		List<List<String>> tokens = SelectorUtils.tokenizeSelector("#a,div.b h1,#c");
		
		Assert.assertEquals("Correct number of selectors", 3, tokens.size());
		Assert.assertEquals("Correct number of tokens for selector 1", 1, tokens.get(0).size());
		Assert.assertEquals("Correct number of tokens for selector 2", 2, tokens.get(1).size());
		Assert.assertEquals("Correct number of tokens for selector 3", 1, tokens.get(2).size());
		
		Assert.assertEquals("#a", tokens.get(0).get(0));
		Assert.assertEquals("div.b", tokens.get(1).get(0));
		Assert.assertEquals("h1", tokens.get(1).get(1));
		Assert.assertEquals("#c", tokens.get(2).get(0));
	}
	
	@Test
	public void tokenizeSelectorTest_sequentialSpaces() {
		
		List<List<String>> tokens = SelectorUtils.tokenizeSelector("#a,#b   .c");
	
		Assert.assertEquals("Correct number of selectors", 2, tokens.size());
		Assert.assertEquals("Correct number of tokens for selector 1", 1, tokens.get(0).size());
		Assert.assertEquals("Correct number of tokens for selector 2", 2, tokens.get(1).size());
		
		Assert.assertEquals("#a", tokens.get(0).get(0));
		Assert.assertEquals("#b", tokens.get(1).get(0));
		Assert.assertEquals(".c", tokens.get(1).get(1));
	}
}
