package com.arrested.research.optimization.suggestion;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IdBasedOptimizationSuggestionEngineTest {

	private IdBasedOptimizationSuggestionEngine engine;
	
	@Before
	public void init() {
		engine = new IdBasedOptimizationSuggestionEngine();
	}
	
	@Test
	public void getSuggestionsTest_noLeadingId() {
		
		Set<String> suggestions = engine.getSuggestions(".test");
		
		Assert.assertEquals(1, suggestions.size());
		Assert.assertTrue(suggestions.contains(IdBasedOptimizationSuggestionEngine.REWRITE_SELECTOR_WITH_ID_FIRST));
	}
	
	
	@Test
	public void isIdSelectorTest_positive() {
		Assert.assertTrue(engine.isIdSelector("#foo-BAR_bat"));
	}
	
	@Test
	public void isIdSelectorTest_negative() {
		Assert.assertFalse(engine.isIdSelector("foo-BAR_bat"));
	}
	
	@Test
	public void isIdSelectorTest_idSelectorNotFirst() {
		Assert.assertFalse(engine.isIdSelector(".baz#foo"));
	}
}
